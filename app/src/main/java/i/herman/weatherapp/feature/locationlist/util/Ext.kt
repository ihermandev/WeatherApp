package i.herman.weatherapp.feature.locationlist.util

import com.google.android.libraries.places.api.model.Place
import i.herman.weatherapp.feature.locationlist.model.LocationItem
import kotlinx.datetime.Clock

fun Place.asExternalModel() = LocationItem(
    name = name ?: "",
    lat = latLng?.latitude ?: 0.0,
    lng = latLng?.longitude ?: 0.0,
    publishDate = Clock.System.now()
)
