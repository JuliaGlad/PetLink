package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.MviEffect

sealed interface DayDataEffect: MviEffect {

    data object OpenAddEventActivity: DayDataEffect

    class OpenEventDetailsActivity(
        val eventId: String,
        val title: String,
        val time: String,
        val date: String,
        val theme: String,
        val isNotificationOn: Boolean
    ): DayDataEffect

}