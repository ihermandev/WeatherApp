package i.herman.weatherapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : BaseViewState, VI : BaseIntent, VE : BaseViewEvent> :
    ViewModel() {

    private val initialState: VS by lazy { createInitialState() }

    private val viewIntentFlow =
        MutableSharedFlow<VI>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val _viewEventFlow = MutableSharedFlow<VE>()
    val viewEventFlow: SharedFlow<VE> = _viewEventFlow

    /**
     * Current state of the view. This state is updated
     * every time an event is produced in the ViewModel.
     */
    private val _viewStateFlow = MutableStateFlow(initialState)
    val viewStateFlow: StateFlow<VS> = _viewStateFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialState
        )

    /**
     * Returns the initial state of the view.
     */
    abstract fun createInitialState(): VS

    /**
     * Handles the intent of type BaseViewIntent produced **from** the view.
     * Each intent is mapped to a StateReducer to update the state of the view.
     */
    abstract fun Flow<VI>.handleIntent(): Flow<StateReducer<VS>>

    /**
     * Process an intent of type BaseViewIntent. This intent
     * will produce a change in the current state of the view.
     */
    fun processIntent(intent: VI) = viewModelScope.launch {
        viewIntentFlow.emit(intent)
    }

    /**
     * Dispatch an event of type BaseViewEvent. This event
     * will produce a change in the view event flow.
     */
    private fun dispatchEvent(event: VE) =
        viewModelScope.launch {
            _viewEventFlow.emit(event)
        }

    protected open fun getViewEventFromStateReducer(state: StateReducer<VS>): VE? =
        null

    init {
        viewModelScope.launch {
            viewIntentFlow
                .handleIntent()
                .onEach { getViewEventFromStateReducer(it)?.let(::dispatchEvent) }
                .scan(initialState) { viewState, change -> change.reduce(viewState) }
                .onEach { setState(it) }
                .launchIn(viewModelScope)
        }
    }


    private fun setState(state: VS) {
        _viewStateFlow.value = state
    }
}
