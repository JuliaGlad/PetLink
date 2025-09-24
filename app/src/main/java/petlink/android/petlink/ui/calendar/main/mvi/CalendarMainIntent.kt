package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CalendarMainIntent: MviIntent {

    data object LoadCalendarEvents: CalendarMainIntent

}