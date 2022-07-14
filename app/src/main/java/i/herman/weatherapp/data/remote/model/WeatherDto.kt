package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("daily")
    val daily: WeatherDataDto,
    @SerializedName("daily_units")
    val daily_units: WeatherUnitsDto,
    @SerializedName("elevation")
    val elevation: Double,
    @SerializedName("generationtime_ms")
    val generationTimeMs: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
)
