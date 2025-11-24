package petlink.android.feature_calendar_ui_add_event.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface AddEventPartialState: MviPartialState {

    data object Loading: AddEventPartialState

    class EventCreated(val eventId: String): AddEventPartialState

    class Error(val throwable: Throwable): AddEventPartialState

}