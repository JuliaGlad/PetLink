package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import petlink.android.core_mvi.MviIntent

sealed interface MonthViewIntent: MviIntent {

    class GetEventsByMonth(
        val year: Int,
        val month: Int
    ): MonthViewIntent

}