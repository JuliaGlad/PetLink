package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi

import petlink.android.core_mvi.MviIntent

sealed interface MonthViewIntent: MviIntent {

    class GetEventsByMonth(
        val year: Int,
        val month: Int
    ): MonthViewIntent

}