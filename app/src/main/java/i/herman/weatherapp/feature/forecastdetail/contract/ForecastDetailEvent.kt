package i.herman.weatherapp.feature.forecastdetail.contract

import i.herman.weatherapp.base.BaseViewEvent

sealed class ForecastDetailEvent : BaseViewEvent {

    object OnBackClick : ForecastDetailEvent()
}
