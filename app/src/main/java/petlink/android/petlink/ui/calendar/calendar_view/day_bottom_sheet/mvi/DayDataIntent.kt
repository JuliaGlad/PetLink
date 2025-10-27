package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.MviIntent

sealed interface DayDataIntent: MviIntent {

    class LoadDayEvents(val date: String): DayDataIntent

}