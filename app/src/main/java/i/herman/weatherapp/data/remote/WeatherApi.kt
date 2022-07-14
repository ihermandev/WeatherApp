package i.herman.weatherapp.data.remote

import i.herman.weatherapp.data.remote.model.WeatherDto
import retrofit2.Response as RetrofitResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?timezone=UTC&daily=temperature_2m_max,temperature_2m_min,weathercode")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
    ): RetrofitResponse<WeatherDto>
}

