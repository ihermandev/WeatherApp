package i.herman.weatherapp.feature.locationlist.viewmodel

import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import i.herman.weatherapp.base.BaseViewModel
import i.herman.weatherapp.base.StateReducer
import i.herman.weatherapp.data.repository.LocationListRepository
import i.herman.weatherapp.feature.locationlist.contract.LocationListEvent
import i.herman.weatherapp.feature.locationlist.contract.LocationListStateReducer
import i.herman.weatherapp.feature.locationlist.contract.LocationListViewIntent
import i.herman.weatherapp.feature.locationlist.contract.LocationListViewState
import i.herman.weatherapp.feature.locationlist.model.LocationListState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val placesClient: PlacesClient,
    private val repository: LocationListRepository,
) : BaseViewModel<LocationListViewState, LocationListViewIntent, LocationListEvent>() {

    init {
        processIntent(LocationListViewIntent.FetchLocations)
    }

    override fun createInitialState(): LocationListViewState =
        LocationListViewState(state = LocationListState.LoadingLocationList)

    @OptIn(FlowPreview::class)
    override fun Flow<LocationListViewIntent>.handleIntent(): Flow<StateReducer<LocationListViewState>> {

        val fetchLocationList =
            filterIsInstance<LocationListViewIntent.FetchLocations>()
                .flatMapConcat {
                    repository.getLocations().map { locations ->
                        if (locations.isNotEmpty()) LocationListStateReducer.LoadedLocationList(
                            locations = locations) else LocationListStateReducer.EmptyList
                    }.onStart {
                        emit(LocationListStateReducer.LoadList)
                    }
                }

        val locationChosenFlow = filterIsInstance<LocationListViewIntent.AddLocation>()
            .flatMapConcat { addLocationIntent ->
                repository.insertLocation(addLocationIntent.location)
                    .map { locationItem ->
                        LocationListStateReducer.LocationChosen(locationItem)
                    }
            }

        val locationRemoveFlow = filterIsInstance<LocationListViewIntent.RemoveLocation>()
            .flatMapConcat { removeIntent ->
                repository.removeLocation(removeIntent.location)
                    .map { locationItem ->
                        LocationListStateReducer.RemoveLocation(locationItem)
                    }
            }

        return merge(
            fetchLocationList,
            locationChosenFlow,
            locationRemoveFlow
        )
    }
}
