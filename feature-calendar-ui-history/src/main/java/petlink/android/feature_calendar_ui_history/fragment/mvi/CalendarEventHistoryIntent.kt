package petlink.android.feature_calendar_ui_history.fragment.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CalendarEventHistoryIntent: MviIntent {

    data object LoadEvents: CalendarEventHistoryIntent

}