package i.herman.weatherapp.locationdetail.contract

import i.herman.weatherapp.base.StateReducer
import i.herman.weatherapp.locationdetail.model.ForecastItem
import i.herman.weatherapp.locationdetail.model.LocationDetailState

sealed class LocationDetailStateReducer : StateReducer<LocationDetailViewState> {

    object EmptyList : LocationDetailStateReducer() {
        override fun reduce(initialState: LocationDetailViewState): LocationDetailViewState =
            initialState.copy(state = LocationDetailState.EmptyForecast)
    }

    object LoadList : LocationDetailStateReducer() {
        override fun reduce(initialState: LocationDetailViewState): LocationDetailViewState =
            initialState.copy(state = LocationDetailState.LoadingForecast)
    }

    class LoadedForecastList(private val forecasts: List<ForecastItem>) :
        LocationDetailStateReducer() {
        override fun reduce(initialState: LocationDetailViewState): LocationDetailViewState =
            initialState.copy(state = LocationDetailState.ForecastList(forecasts))
    }

    class LocationChosen(private val forecast: ForecastItem) : LocationDetailStateReducer() {
        override fun reduce(initialState: LocationDetailViewState): LocationDetailViewState =
            initialState.copy(state = LocationDetailState.ForecastChosen(forecast))
    }
}
