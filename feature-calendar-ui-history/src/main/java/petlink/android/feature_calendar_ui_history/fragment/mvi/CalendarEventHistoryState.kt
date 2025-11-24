package petlink.android.feature_calendar_ui_history.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_calendar_ui_history.fragment.model.ListCalendarEventUiModel

data class CalendarEventHistoryState(val value: LceState<ListCalendarEventUiModel>): MviState