package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherHourlyDataDto(
    @SerializedName("apparent_temperature")
    val apparentTemperature: List<Double>,
    @SerializedName("direct_radiation")
    val directRadiation: List<Double>,
    @SerializedName("relativehumidity_2m")
    val relativeHumidity2m: List<Double>,
    @SerializedName("surface_pressure")
    val surfacePressure: List<Double>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("weathercode")
    val weatherCode: List<Double>,
)
