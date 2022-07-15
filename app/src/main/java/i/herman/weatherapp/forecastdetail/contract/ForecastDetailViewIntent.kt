package i.herman.weatherapp.forecastdetail.contract

import i.herman.weatherapp.base.BaseIntent

sealed class ForecastDetailViewIntent : BaseIntent {

    object FetchDetailedWeatherData : ForecastDetailViewIntent()

    object OnBackClick : ForecastDetailViewIntent()
}
