package i.herman.weatherapp.locationlist.model

sealed class LocationListState {

    object EmptyLocationList : LocationListState()

    object LoadingLocationList : LocationListState()

    data class LocationList(val locations: List<LocationItem>) : LocationListState()

    data class LocationChosen(val location: LocationItem) : LocationListState()

    data class RemoveLocation(val location: LocationItem) : LocationListState()
}
