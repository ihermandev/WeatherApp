package i.herman.weatherapp.data.repository

import i.herman.weatherapp.data.Response
import i.herman.weatherapp.data.remote.WeatherApi
import i.herman.weatherapp.data.remote.model.WeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    val api: WeatherApi,
) : WeatherRepository {

    override fun getWeatherData(lat: Double, lng: Double): Flow<Response<WeatherDto>> = flow {
        with(api.getWeatherData(lat = lat, lng = lng)) {
            val responseBody = body()
            if (isSuccessful && responseBody != null) {
                emit(Response.Success(responseBody))
            } else {
                emit(Response.Error(message()))
            }
        }
    }
}
