package petlink.android.petlink.ui.calendar.edit_event.mvi

import petlink.android.core_mvi.MviIntent

sealed interface EditEventIntent: MviIntent {

    data object Loading: EditEventIntent

    class SaveEventData(
        val eventId: String,
        val title: String,
        val date: String,
        val theme: String,
        val time: String,
        val dateForTimestamp: String,
        val isNotificationOn: Boolean
    ): EditEventIntent

    class DeleteEvent(
        val eventId: String
    ): EditEventIntent

}