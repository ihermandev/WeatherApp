package i.herman.weatherapp.feature.locationlist.model

import i.herman.weatherapp.data.local.model.LocationEntity
import kotlinx.datetime.Instant

data class LocationItem(
    val id: Long = 0,
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val publishDate: Instant,
)

fun LocationItem.asEntityModel() = LocationEntity(
    id = id,
    name = name,
    lat = lat,
    lng = lng,
    publishDate = publishDate,
)

