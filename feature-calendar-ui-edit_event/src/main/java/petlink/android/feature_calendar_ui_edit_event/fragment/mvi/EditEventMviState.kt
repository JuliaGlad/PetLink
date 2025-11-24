package petlink.android.feature_calendar_ui_edit_event.fragment.mvi

import petlink.android.core_mvi.MviState
import petlink.android.feature_calendar_ui_edit_event.fragment.model.MutableEventModel

data class EditEventMviState(
    val value: EditEventState,
    val event: MutableEventModel = MutableEventModel()
): MviState

sealed interface EditEventState{
    data object Init: EditEventState

    data object Loading: EditEventState

    data object EventUpdated: EditEventState

    data object EventDeleted: EditEventState

    class Error(val throwable: Throwable): EditEventState
}