package i.herman.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import i.herman.weatherapp.data.repository.LocationListRepository
import i.herman.weatherapp.data.repository.LocationListRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsLocationRepository(locationRepository: LocationListRepositoryImpl): LocationListRepository
}
