package i.herman.weatherapp.base

interface StateReducer<VS : BaseViewState> {
    fun reduce(initialState: VS): VS
}
