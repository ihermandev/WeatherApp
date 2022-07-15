package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherDailyDataDto(
    @SerializedName("sunrise")
    val sunrise: List<String>,
    @SerializedName("sunset")
    val sunset: List<String>,
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("windspeed_10m_max")
    val windSpeed10mMax: List<Double>,
    @SerializedName("weathercode")
    val weatherCode: List<Double>,
)
