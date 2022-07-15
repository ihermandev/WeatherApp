package i.herman.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import i.herman.weatherapp.data.repository.LocationListRepository
import i.herman.weatherapp.data.repository.LocationListRepositoryImpl
import i.herman.weatherapp.data.repository.WeatherRepository
import i.herman.weatherapp.data.repository.WeatherRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsLocationRepository(locationRepository: LocationListRepositoryImpl): LocationListRepository

    @Binds
    fun bindsWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository
}
