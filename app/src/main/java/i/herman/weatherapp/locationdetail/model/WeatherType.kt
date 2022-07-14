/*
 * https://github.com/philipplackner/WeatherApp/tree/initial
 */

package i.herman.weatherapp.locationdetail.model

import androidx.annotation.DrawableRes
import i.herman.weatherapp.R

sealed class WeatherType(
    val weatherDescription: String,
    @DrawableRes
    val weatherIcon: Int,
) {
    object Sunny : WeatherType(
        weatherDescription = "Sunny",
        weatherIcon = R.drawable.ic_weather_sunny
    )

    object MostlyClear : WeatherType(
        weatherDescription = "Mostly Clear",
        weatherIcon = R.drawable.ic_weather_cloudy
    )

    object PartlyCloudy : WeatherType(
        weatherDescription = "Partly cloudy",
        weatherIcon = R.drawable.ic_weather_cloudy
    )

    object Cloudy : WeatherType(
        weatherDescription = "Cloudy",
        weatherIcon = R.drawable.ic_weather_cloudy
    )

    object Foggy : WeatherType(
        weatherDescription = "Foggy",
        weatherIcon = R.drawable.ic_weather_very_cloudy
    )

    object DepositingRimeFog : WeatherType(
        weatherDescription = "Depositing rime fog",
        weatherIcon = R.drawable.ic_weather_very_cloudy
    )

    object LightDrizzle : WeatherType(
        weatherDescription = "Light drizzle",
        weatherIcon = R.drawable.ic_weather_rainshower
    )

    object ModerateDrizzle : WeatherType(
        weatherDescription = "Moderate drizzle",
        weatherIcon = R.drawable.ic_weather_rainshower
    )

    object DenseDrizzle : WeatherType(
        weatherDescription = "Dense drizzle",
        weatherIcon = R.drawable.ic_weather_rainshower
    )

    object LightFreezingDrizzle : WeatherType(
        weatherDescription = "Slight freezing drizzle",
        weatherIcon = R.drawable.ic_weather_snowyrainy
    )

    object DenseFreezingDrizzle : WeatherType(
        weatherDescription = "Dense freezing drizzle",
        weatherIcon = R.drawable.ic_weather_snowyrainy
    )

    object SlightRain : WeatherType(
        weatherDescription = "Slight rain",
        weatherIcon = R.drawable.ic_weather_rainy
    )

    object ModerateRain : WeatherType(
        weatherDescription = "Rainy",
        weatherIcon = R.drawable.ic_weather_rainy
    )

    object HeavyRain : WeatherType(
        weatherDescription = "Heavy rain",
        weatherIcon = R.drawable.ic_weather_rainy
    )

    object HeavyFreezingRain : WeatherType(
        weatherDescription = "Heavy freezing rain",
        weatherIcon = R.drawable.ic_weather_snowyrainy
    )

    object SlightSnowFall : WeatherType(
        weatherDescription = "Slight snow fall",
        weatherIcon = R.drawable.ic_weather_snowy
    )

    object ModerateSnowFall : WeatherType(
        weatherDescription = "Moderate snow fall",
        weatherIcon = R.drawable.ic_weather_heavysnow
    )

    object HeavySnowFall : WeatherType(
        weatherDescription = "Heavy snow fall",
        weatherIcon = R.drawable.ic_weather_heavysnow
    )

    object SnowGrains : WeatherType(
        weatherDescription = "Snow grains",
        weatherIcon = R.drawable.ic_weather_heavysnow
    )

    object SlightRainShowers : WeatherType(
        weatherDescription = "Slight rain showers",
        weatherIcon = R.drawable.ic_weather_rainshower
    )

    object ModerateRainShowers : WeatherType(
        weatherDescription = "Moderate rain showers",
        weatherIcon = R.drawable.ic_weather_rainshower
    )

    object ViolentRainShowers : WeatherType(
        weatherDescription = "Violent rain showers",
        weatherIcon = R.drawable.ic_weather_rainshower
    )

    object SlightSnowShowers : WeatherType(
        weatherDescription = "Light snow showers",
        weatherIcon = R.drawable.ic_weather_snowy
    )

    object HeavySnowShowers : WeatherType(
        weatherDescription = "Heavy snow showers",
        weatherIcon = R.drawable.ic_weather_snowy
    )

    object ModerateThunderstorm : WeatherType(
        weatherDescription = "Moderate thunderstorm",
        weatherIcon = R.drawable.ic_weather_thunder
    )

    object SlightHailThunderstorm : WeatherType(
        weatherDescription = "Thunderstorm with slight hail",
        weatherIcon = R.drawable.ic_weather_rainythunder
    )

    object HeavyHailThunderstorm : WeatherType(
        weatherDescription = "Thunderstorm with heavy hail",
        weatherIcon = R.drawable.ic_weather_rainythunder
    )

    companion object {
        fun fromWmoCode(code: Int): WeatherType {
            return when (code) {
                0 -> Sunny
                1 -> MostlyClear
                2 -> PartlyCloudy
                3 -> Cloudy
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> Sunny
            }
        }
    }
}
