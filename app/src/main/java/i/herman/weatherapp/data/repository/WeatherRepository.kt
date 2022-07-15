package i.herman.weatherapp.data.repository

import i.herman.weatherapp.data.Response
import i.herman.weatherapp.data.remote.model.WeatherDetailedDto
import i.herman.weatherapp.data.remote.model.WeatherDto
import kotlinx.coroutines.flow.Flow
import java.time.ZoneId

interface WeatherRepository {

    fun getWeatherData(
        lat: Double,
        lng: Double,
        timeZone: String = ZoneId.systemDefault().toString(),
    ): Flow<Response<WeatherDto>>

    fun getDetailedWeatherData(
        lat: Double,
        lng: Double,
        timeZone: String = ZoneId.systemDefault().toString(),
    ): Flow<Response<WeatherDetailedDto>>
}
