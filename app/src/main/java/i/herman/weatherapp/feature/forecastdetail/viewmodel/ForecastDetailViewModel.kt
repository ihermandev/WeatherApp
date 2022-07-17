package i.herman.weatherapp.feature.forecastdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import i.herman.weatherapp.base.BaseViewModel
import i.herman.weatherapp.base.StateReducer
import i.herman.weatherapp.data.Response
import i.herman.weatherapp.data.remote.model.asForecastDetailedItem
import i.herman.weatherapp.data.repository.WeatherRepository
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailEvent
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailStateReducer
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailViewIntent
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailViewState
import i.herman.weatherapp.feature.forecastdetail.model.ForecastDetailState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ForecastDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val repository: WeatherRepository,
) : BaseViewModel<ForecastDetailViewState, ForecastDetailViewIntent, ForecastDetailEvent>() {

    private val date: String = checkNotNull(savedStateHandle["date"])
    val lat: String = checkNotNull(savedStateHandle["lat"])
    val lng: String = checkNotNull(savedStateHandle["lng"])

    init {
        processIntent(ForecastDetailViewIntent.FetchDetailedWeatherData)
    }

    override fun createInitialState(): ForecastDetailViewState =
        ForecastDetailViewState(state = ForecastDetailState.LoadingForecast)

    @OptIn(FlowPreview::class)
    override fun Flow<ForecastDetailViewIntent>.handleIntent(): Flow<StateReducer<ForecastDetailViewState>> {

        val fetchWeatherDetails =
            filterIsInstance<ForecastDetailViewIntent.FetchDetailedWeatherData>()
                .flatMapConcat {
                    repository.getDetailedWeatherData(lat = lat.toDouble(), lng = lng.toDouble())
                        .map { response ->
                            when (response) {
                                is Response.Success -> {
                                    val test = response.data.asForecastDetailedItem(date)
                                    ForecastDetailStateReducer.LoadedDetailedForecast(forecast = test)

                                }
                                is Response.Error -> {
                                    //TODO handle error
                                    ForecastDetailStateReducer.EmptyForecast
                                }
                            }
                        }.onStart {
                            emit(ForecastDetailStateReducer.LoadForecast)
                        }
                }

        return merge(fetchWeatherDetails)
    }
}
