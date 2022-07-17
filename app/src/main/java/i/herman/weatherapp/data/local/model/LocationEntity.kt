package i.herman.weatherapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import i.herman.weatherapp.feature.locationlist.model.LocationItem
import kotlinx.datetime.Instant

@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
)

fun LocationEntity.asExternalModel() = LocationItem(
    id = id,
    name = name,
    lat = lat,
    lng = lng,
    publishDate = publishDate,
)
