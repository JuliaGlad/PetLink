package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviIntent

sealed interface AddEventIntent: MviIntent {

    class AddEvent(
        val title: String,
        val date: String,
        val theme: String,
        val time: String,
        val dateForTimestamp: String,
        val isNotificationOn: Boolean
    ): AddEventIntent

    object Loading: AddEventIntent
}