package i.herman.weatherapp.locationlist.contract

import i.herman.weatherapp.locationlist.model.LocationListState
import i.herman.weatherapp.base.BaseViewState

data class LocationListViewState(val state: LocationListState) : BaseViewState

