package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherDailyUnitsDto(
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("windspeed_10m_max")
    val windSpeed10mMax: String,
    @SerializedName("weathercode")
    val weatherCode: String,
)
