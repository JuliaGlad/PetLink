package petlink.android.feature_calendar_ui_edit_event.fragment.mvi

import petlink.android.core_mvi.MviEffect
import petlink.android.core_navigation.action.EditEventAction

sealed interface EditEventEffect: MviEffect {

    data object ShowDeleteEventDialog: EditEventEffect

    data object FinishActivity: EditEventEffect

    data object ShowDataDialog: EditEventEffect

    data object ShowTimeDialog: EditEventEffect

    class FinishActivityAfterUpdate(
        val action: EditEventAction,
        val eventId: String,
        val title: String,
        val date: String,
        val theme: String,
        val time: String,
        val isNotificationOn: Boolean
    ): EditEventEffect

    class FinishActivityAfterDelete(
        val action: EditEventAction,
        val eventId: String,
    ): EditEventEffect
}