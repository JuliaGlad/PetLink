package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface AddEventPartialState: MviPartialState {

    data object Loading: AddEventPartialState

    data object EventCreated: AddEventPartialState

    class Error(val throwable: Throwable): AddEventPartialState

}