package i.herman.weatherapp.locationlist.contract

import i.herman.weatherapp.base.BaseViewEvent

sealed class LocationListEvent : BaseViewEvent {

    object OnLocationDetailsClick : LocationListEvent()
}
