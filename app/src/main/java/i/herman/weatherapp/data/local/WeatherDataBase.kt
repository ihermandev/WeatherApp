package i.herman.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import i.herman.weatherapp.data.local.dao.LocationDao
import i.herman.weatherapp.data.local.model.LocationEntity

@Database(
    entities = [LocationEntity::class], version = 1, exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
