package i.herman.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName
import i.herman.weatherapp.feature.forecastdetail.model.ForecastDetailedItem
import i.herman.weatherapp.feature.forecastdetail.model.UvIndex
import i.herman.weatherapp.feature.locationdetail.model.WeatherType

data class WeatherDetailedDto(
    @SerializedName("daily")
    val daily: WeatherDailyDataDto,
    @SerializedName("daily_units")
    val daily_units: WeatherDailyUnitsDto,
    @SerializedName("elevation")
    val elevation: Double,
    @SerializedName("generationtime_ms")
    val generationTimeMs: Double,
    @SerializedName("hourly")
    val hourly: WeatherHourlyDataDto,
    @SerializedName("hourly_units")
    val hourly_units: WeatherHourlyUnitsDto,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
)

fun WeatherDetailedDto.asForecastDetailedItem(date: String): ForecastDetailedItem {
    val dailyIndex = daily.time.indexOfFirst { it == date }
    val hourlyIndexes = hourly.time.withIndex().filter { it.value.contains(date) }

    val time = hourlyIndexes.map { hourly.time[it.index] }
    val apparentTemperature = hourlyIndexes.map { hourly.apparentTemperature[it.index] }
    val radiation =
        hourlyIndexes.map { UvIndex.fromValue(hourly.directRadiation[it.index].toInt()) }
    val pressure = hourlyIndexes.map { hourly.surfacePressure[it.index] }
    val temperature = hourlyIndexes.map { hourly.temperature2m[it.index] }
    val humidity = hourlyIndexes.map { hourly.relativeHumidity2m[it.index] }
    val weatherType =
        hourlyIndexes.map { WeatherType.fromWmoCode(hourly.weatherCode[it.index].toInt()) }

    return ForecastDetailedItem(
        sunrise = daily.sunrise[dailyIndex],
        sunset = daily.sunset[dailyIndex],
        currentDate = daily.time[dailyIndex],
        windSpeed = daily.windSpeed10mMax[dailyIndex],
        weatherType = WeatherType.fromWmoCode(daily.weatherCode[dailyIndex].toInt()),
        time = time,
        apparentTemperature = apparentTemperature,
        directRadiation = radiation,
        temperature = temperature,
        surfacePressure = pressure,
        relativeHumidity = humidity,
        weatherTypeHourly = weatherType
    )
}
