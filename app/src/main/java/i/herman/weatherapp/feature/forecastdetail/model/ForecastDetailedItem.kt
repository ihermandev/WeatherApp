package i.herman.weatherapp.feature.forecastdetail.model

import i.herman.weatherapp.feature.locationdetail.model.WeatherType
import java.time.LocalDateTime


data class ForecastDetailedItem(
    val sunrise: String,
    val sunset: String,
    val currentDate: String,
    val windSpeed: Double,
    val weatherType: WeatherType,
    val time: List<String>,
    val apparentTemperature: List<Double>,
    val temperature: List<Double>,
    val surfacePressure: List<Double>,
    val relativeHumidity: List<Double>,
    val directRadiation: List<UvIndex>,
    val weatherTypeHourly: List<WeatherType>,
)

data class ForecastDetailedCurrentItem(
    val time: String,
    val apparentTemperature: Double,
    val temperature: Double,
    val pressure: Double,
    val humidity: Double,
    val weatherType: WeatherType,
    val uvIndex: UvIndex,
)

fun ForecastDetailedItem.asListOfTime(): List<String> = time.map { it.substringAfter("T") }

fun justTime(date: String): String = date.substringAfter("T")

fun ForecastDetailedItem.asForecastDetailedCurrentItem(): ForecastDetailedCurrentItem {
    val now = LocalDateTime.now()
    val currentHour = if (now.minute < 30) now.hour else now.hour + 1
    return ForecastDetailedCurrentItem(
        time = time[currentHour],
        apparentTemperature = apparentTemperature[currentHour],
        temperature = temperature[currentHour],
        pressure = surfacePressure[currentHour],
        humidity = relativeHumidity[currentHour],
        uvIndex = directRadiation[currentHour],
        weatherType = weatherTypeHourly[currentHour],
    )
}
