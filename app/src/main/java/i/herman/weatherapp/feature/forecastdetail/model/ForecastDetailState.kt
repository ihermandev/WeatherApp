package i.herman.weatherapp.feature.forecastdetail.model

sealed class ForecastDetailState {

    object EmptyForecast : ForecastDetailState()

    object LoadingForecast : ForecastDetailState()

    data class ForecastDetailedData(val forecast: ForecastDetailedItem) : ForecastDetailState()
}
