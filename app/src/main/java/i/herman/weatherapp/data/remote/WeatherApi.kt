package i.herman.weatherapp.data.remote

import i.herman.weatherapp.data.remote.model.WeatherDetailedDto
import i.herman.weatherapp.data.remote.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response as RetrofitResponse

interface WeatherApi {

    @GET("v1/forecast?" +
            "&daily=temperature_2m_max,temperature_2m_min,weathercode")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("timezone") timeZone: String
    ): RetrofitResponse<WeatherDto>

    @GET("v1/forecast?" +
            "&hourly=temperature_2m,relativehumidity_2m,apparent_temperature,surface_pressure,direct_radiation,weathercode" +
            "&daily=weathercode,sunrise,sunset,windspeed_10m_max")
    suspend fun getDetailedWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("timezone") timeZone: String
    ): RetrofitResponse<WeatherDetailedDto>

}

