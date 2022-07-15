package i.herman.weatherapp.forecastdetail.contract

import i.herman.weatherapp.base.StateReducer
import i.herman.weatherapp.forecastdetail.model.ForecastDetailState
import i.herman.weatherapp.forecastdetail.model.ForecastDetailedItem

sealed class ForecastDetailStateReducer : StateReducer<ForecastDetailViewState> {

    object EmptyForecast : ForecastDetailStateReducer() {
        override fun reduce(initialState: ForecastDetailViewState): ForecastDetailViewState =
            initialState.copy(state = ForecastDetailState.EmptyForecast)
    }

    object LoadForecast : ForecastDetailStateReducer() {
        override fun reduce(initialState: ForecastDetailViewState): ForecastDetailViewState =
            initialState.copy(state = ForecastDetailState.LoadingForecast)
    }

    class LoadedDetailedForecast(private val forecast: ForecastDetailedItem) :
        ForecastDetailStateReducer() {
        override fun reduce(initialState: ForecastDetailViewState): ForecastDetailViewState =
            initialState.copy(state = ForecastDetailState.ForecastDetailedData(forecast = forecast))
    }
}
