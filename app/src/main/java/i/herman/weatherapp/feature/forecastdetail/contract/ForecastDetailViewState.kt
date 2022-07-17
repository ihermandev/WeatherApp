package i.herman.weatherapp.feature.forecastdetail.contract

import i.herman.weatherapp.base.BaseViewState
import i.herman.weatherapp.feature.forecastdetail.model.ForecastDetailState

data class ForecastDetailViewState(val state: ForecastDetailState) : BaseViewState
