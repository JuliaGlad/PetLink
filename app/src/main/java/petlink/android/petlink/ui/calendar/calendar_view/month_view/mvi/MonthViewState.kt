package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.calendar.calendar_view.month_view.model.ListCalendarEventWithTimestampUi

data class MonthViewState(
    val value: LceState<ListCalendarEventWithTimestampUi>
): MviState