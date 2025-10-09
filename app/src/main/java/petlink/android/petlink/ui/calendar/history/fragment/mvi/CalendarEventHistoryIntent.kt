package petlink.android.petlink.ui.calendar.history.fragment.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CalendarEventHistoryIntent: MviIntent {

    data object LoadEvents: CalendarEventHistoryIntent

}