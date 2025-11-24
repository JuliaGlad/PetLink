package petlink.android.feature_calendar_ui_main.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CalendarMainIntent: MviIntent {

    data object LoadCalendarEvents: CalendarMainIntent

}