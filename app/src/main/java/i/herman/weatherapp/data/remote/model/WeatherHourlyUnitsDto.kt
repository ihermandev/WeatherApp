package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherHourlyUnitsDto(
    @SerializedName("apparent_temperature")
    val apparentTemperature: String,
    @SerializedName("direct_radiation")
    val directRadiation: String,
    @SerializedName("relativehumidity_2m")
    val relativeHumidity2m: String,
    @SerializedName("surface_pressure")
    val surfacePressure: String,
    @SerializedName("temperature_2m")
    val temperature2m: String,
    @SerializedName("time")
    val time: String,
)
