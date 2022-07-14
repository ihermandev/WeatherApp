package i.herman.weatherapp.data.repository

import i.herman.weatherapp.data.Response
import i.herman.weatherapp.data.remote.model.WeatherDto
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherData(lat: Double, lng: Double): Flow<Response<WeatherDto>>
}
