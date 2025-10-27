package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.calendar.model.MutableEventModel

data class AddEventMviState(
    val value: AddEventState,
    val newEventModel: MutableEventModel = MutableEventModel()
): MviState

sealed interface AddEventState{
    data object Init: AddEventState
    data object Loading: AddEventState
    class EventCreated(val eventId: String): AddEventState
    class Error(val throwable: Throwable): AddEventState
}