package petlink.android.petlink.ui.calendar.edit_event.mvi

import petlink.android.core_mvi.MviEffect
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventEffect

sealed interface EditEventEffect: MviEffect {

    data object ShowDeleteEventDialog: EditEventEffect

    data object FinishActivity: EditEventEffect

    data object ShowDataDialog: EditEventEffect

    data object ShowTimeDialog: EditEventEffect

    class FinishActivityAfterUpdate(
        val actionId: Int,
        val eventId: String,
        val title: String,
        val date: String,
        val theme: String,
        val time: String,
        val isNotificationOn: Boolean
    ): EditEventEffect

    class FinishActivityAfterDelete(
        val actionId: Int,
        val eventId: String,
    ): EditEventEffect
}