package i.herman.weatherapp.forecastdetail.contract

import i.herman.weatherapp.base.BaseViewEvent

sealed class ForecastDetailEvent : BaseViewEvent {

    object OnBackClick : ForecastDetailEvent()
}
