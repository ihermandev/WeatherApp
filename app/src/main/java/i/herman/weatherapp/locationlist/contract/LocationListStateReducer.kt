package i.herman.weatherapp.locationlist.contract

import i.herman.weatherapp.base.StateReducer
import i.herman.weatherapp.locationlist.model.LocationItem
import i.herman.weatherapp.locationlist.model.LocationListState

sealed class LocationListStateReducer : StateReducer<LocationListViewState> {

    object EmptyList : LocationListStateReducer() {
        override fun reduce(initialState: LocationListViewState): LocationListViewState =
            initialState.copy(state = LocationListState.EmptyLocationList)
    }

    object LoadList : LocationListStateReducer() {
        override fun reduce(initialState: LocationListViewState): LocationListViewState =
            initialState.copy(state = LocationListState.LoadingLocationList)
    }

    class LoadedLocationList(private val locations: List<LocationItem>) :
        LocationListStateReducer() {
        override fun reduce(initialState: LocationListViewState): LocationListViewState =
            initialState.copy(state = LocationListState.LocationList(locations = locations))
    }

    class LocationChosen(private val location: LocationItem) : LocationListStateReducer() {
        override fun reduce(initialState: LocationListViewState): LocationListViewState =
            initialState.copy(state = LocationListState.LocationChosen(location = location))
    }

    class RemoveLocation(private val location: LocationItem) : LocationListStateReducer() {
        override fun reduce(initialState: LocationListViewState): LocationListViewState =
            initialState.copy(state = LocationListState.RemoveLocation(location = location))
    }
}
