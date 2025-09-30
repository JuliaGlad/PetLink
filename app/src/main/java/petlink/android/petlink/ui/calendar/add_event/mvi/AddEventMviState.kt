package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviState

data class AddEventMviState(val value: AddEventState): MviState

sealed interface AddEventState{
    data object Init: AddEventState
    data object Loading: AddEventState
    data object EventCreated: AddEventState
    class Error(val throwable: Throwable): AddEventState
}