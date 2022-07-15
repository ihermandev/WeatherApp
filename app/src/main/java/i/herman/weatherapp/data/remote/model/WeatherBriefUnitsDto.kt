package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherBriefUnitsDto(
    @SerializedName("temperature_2m_max")
    val temperatureMax: String,
    @SerializedName("temperature_2m_min")
    val temperatureMin: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("weathercode")
    val weatherCode: String,
)
