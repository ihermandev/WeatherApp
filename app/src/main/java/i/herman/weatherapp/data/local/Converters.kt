package i.herman.weatherapp.data.local

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

object Converters {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}
