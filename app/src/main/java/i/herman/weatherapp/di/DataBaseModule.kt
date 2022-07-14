package i.herman.weatherapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import i.herman.weatherapp.data.local.WeatherDataBase
import i.herman.weatherapp.data.local.dao.LocationDao
import javax.inject.Singleton

const val DATABASE_NAME = "weather-database"

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun providesLocationDao(
        database: WeatherDataBase,
    ): LocationDao = database.locationDao()

    @Provides
    @Singleton
    fun providesLocationListDatabase(
        @ApplicationContext context: Context,
    ): WeatherDataBase = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        DATABASE_NAME
    ).build()
}
