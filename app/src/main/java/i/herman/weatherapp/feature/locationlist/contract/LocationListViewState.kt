package i.herman.weatherapp.feature.locationlist.contract

import i.herman.weatherapp.feature.locationlist.model.LocationListState
import i.herman.weatherapp.base.BaseViewState

data class LocationListViewState(val state: LocationListState) : BaseViewState

