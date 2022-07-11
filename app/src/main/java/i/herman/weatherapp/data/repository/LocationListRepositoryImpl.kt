package i.herman.weatherapp.data.repository

import i.herman.weatherapp.data.local.dao.LocationDao
import i.herman.weatherapp.data.local.model.LocationEntity
import i.herman.weatherapp.data.local.model.asExternalModel
import i.herman.weatherapp.locationlist.model.LocationItem
import i.herman.weatherapp.locationlist.model.asEntityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationListRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
) : LocationListRepository {

    override fun getLocations(): Flow<List<LocationItem>> =
        locationDao.getAllLocations().map {
            it.map(LocationEntity::asExternalModel)
        }

    override fun insertLocation(location: LocationItem): Flow<LocationItem> = flow {
        locationDao.insertOrIgnoreLocation(location = location.asEntityModel())
        emit(location)
    }

    override fun removeLocation(location: LocationItem): Flow<LocationItem> = flow {
        locationDao.deleteLocation(location = location.asEntityModel())
        emit(location)
    }

}
