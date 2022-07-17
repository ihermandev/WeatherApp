package i.herman.weatherapp.feature.locationlist.contract

import i.herman.weatherapp.base.BaseIntent
import i.herman.weatherapp.feature.locationlist.model.LocationItem

sealed class LocationListViewIntent : BaseIntent {

    object FetchLocations : LocationListViewIntent()

    object OnAddLocationClick : LocationListViewIntent()

    class OnLocationClick(
        val location: String,
        val lat: Double,
        val lng: Double,
    ) : LocationListViewIntent()

    class AddLocation(
        val location: LocationItem,
    ) : LocationListViewIntent()

    class RemoveLocation(
        val location: LocationItem,
    ) : LocationListViewIntent()
}
