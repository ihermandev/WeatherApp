package i.herman.weatherapp.locationdetail.contract

import i.herman.weatherapp.base.BaseIntent

sealed class LocationDetailViewIntent : BaseIntent {

    object FetchSevenDaysWeatherForecast : LocationDetailViewIntent()

    object OnWeatherClick : LocationDetailViewIntent()

    object OnBackClick : LocationDetailViewIntent()
}
