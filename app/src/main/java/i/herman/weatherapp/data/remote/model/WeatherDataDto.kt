package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName
import i.herman.weatherapp.locationdetail.model.ForecastItem
import i.herman.weatherapp.locationdetail.model.WeatherType

data class WeatherDataDto(
    @SerializedName("time")
    val timeList: List<String>,
    @SerializedName("temperature_2m_max")
    val temperatureMaxList: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperatureMinList: List<Double>,
    @SerializedName("weathercode")
    val weatherCodeList: List<Double>,
)

fun WeatherDataDto.toForecastItemList(): List<ForecastItem> {
    return timeList.mapIndexed { index, time ->
        val temperatureMax = temperatureMaxList[index]
        val temperatureMin = temperatureMinList[index]
        val weatherCode = weatherCodeList[index]

        ForecastItem(temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            weatherType = WeatherType.fromWmoCode(weatherCode.toInt()),
            time = time.replace("T00:00", ""))
    }
}
