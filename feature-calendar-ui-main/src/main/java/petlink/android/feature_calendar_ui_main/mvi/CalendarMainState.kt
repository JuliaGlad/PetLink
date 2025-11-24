package petlink.android.feature_calendar_ui_main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_calendar_ui_main.model.ListCalendarEventUiModel

data class CalendarMainState(val value: LceState<ListCalendarEventUiModel>): MviState