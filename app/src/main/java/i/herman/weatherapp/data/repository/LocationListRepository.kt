package i.herman.weatherapp.data.repository

import i.herman.weatherapp.feature.locationlist.model.LocationItem
import kotlinx.coroutines.flow.Flow

interface LocationListRepository {

    fun getLocations(): Flow<List<LocationItem>>
    fun insertLocation(location: LocationItem): Flow<LocationItem>
    fun removeLocation(location: LocationItem): Flow<LocationItem>
}
