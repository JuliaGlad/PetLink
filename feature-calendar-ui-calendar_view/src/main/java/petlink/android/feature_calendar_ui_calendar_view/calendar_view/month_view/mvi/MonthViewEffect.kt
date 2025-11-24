package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi

import petlink.android.core_mvi.MviEffect

sealed interface MonthViewEffect: MviEffect{

    class ShowDayBottomSheet(val date: String): MonthViewEffect

}