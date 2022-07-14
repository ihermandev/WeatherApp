package i.herman.weatherapp.locationdetail.model

sealed class LocationDetailState {

    object EmptyForecast : LocationDetailState()

    object LoadingForecast : LocationDetailState()

    data class ForecastList(val forecasts: List<ForecastItem>) : LocationDetailState()

    data class ForecastChosen(val forecast: ForecastItem) : LocationDetailState()
}
