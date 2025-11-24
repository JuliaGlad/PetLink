package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.ListCalendarEventWithTimestampUi

data class MonthViewState(
    val value: LceState<ListCalendarEventWithTimestampUi>
): MviState