package i.herman.weatherapp.locationlist.contract

import i.herman.weatherapp.base.BaseIntent
import i.herman.weatherapp.locationlist.model.LocationItem

sealed class LocationListViewIntent : BaseIntent {

    object FetchLocations : LocationListViewIntent()

    object OnAddLocationClick : LocationListViewIntent()

    object OnLocationClick : LocationListViewIntent()

    class AddLocation(
        val location: LocationItem,
    ) : LocationListViewIntent()

    class RemoveLocation(
        val location: LocationItem,
    ) : LocationListViewIntent()
}
