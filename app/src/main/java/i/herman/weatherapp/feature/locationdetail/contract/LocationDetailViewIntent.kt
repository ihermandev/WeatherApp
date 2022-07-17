package i.herman.weatherapp.feature.locationdetail.contract

import i.herman.weatherapp.base.BaseIntent

sealed class LocationDetailViewIntent : BaseIntent {

    object FetchSevenDaysWeatherForecast : LocationDetailViewIntent()

    class OnWeatherClick(
        val date: String,
        val lat: String,
        val lng: String,
    ) : LocationDetailViewIntent()

    object OnBackClick : LocationDetailViewIntent()
}
