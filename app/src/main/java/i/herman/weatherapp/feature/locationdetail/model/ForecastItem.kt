package i.herman.weatherapp.feature.locationdetail.model

data class ForecastItem(
    val temperatureMax: Double,
    val temperatureMin: Double,
    val time: String,
    val weatherType: WeatherType,
)
