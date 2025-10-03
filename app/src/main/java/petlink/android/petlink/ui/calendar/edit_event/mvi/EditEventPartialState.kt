package petlink.android.petlink.ui.calendar.edit_event.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface EditEventPartialState: MviPartialState {

    data object Loading: EditEventPartialState

    data object EventUpdated: EditEventPartialState

    data object EventDeleted: EditEventPartialState

    class Error(val throwable: Throwable): EditEventPartialState

}