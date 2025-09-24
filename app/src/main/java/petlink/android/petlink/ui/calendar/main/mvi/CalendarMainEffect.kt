package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CalendarMainEffect: MviEffect {

    data object OpenHistoryFragment: CalendarMainEffect

    data object OpenCalendarViewFragment: CalendarMainEffect

    data object NavigateToAddCalendarEvent: CalendarMainEffect

    class OpenEventDetails(val eventId: String): CalendarMainEffect

}