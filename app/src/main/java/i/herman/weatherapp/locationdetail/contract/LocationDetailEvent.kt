package i.herman.weatherapp.locationdetail.contract

import i.herman.weatherapp.base.BaseViewEvent

sealed class LocationDetailEvent : BaseViewEvent {

    object OnBackClick : LocationDetailEvent()

    class OnWeatherDetailsClick(
        val date: String,
        val lat: String,
        val lng: String,
    ) : LocationDetailEvent()
}
