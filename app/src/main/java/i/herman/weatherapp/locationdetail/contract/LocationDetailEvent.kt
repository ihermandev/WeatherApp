package i.herman.weatherapp.locationdetail.contract

import i.herman.weatherapp.base.BaseViewEvent

sealed class LocationDetailEvent : BaseViewEvent {

    object OnBackClick : LocationDetailEvent()

    object OnWeatherDetailsClick : LocationDetailEvent()
}
