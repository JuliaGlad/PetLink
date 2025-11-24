package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_calendar_ui_calendar_view.model.ListCalendarEventUiModel

data class DayDataState(val value: LceState<ListCalendarEventUiModel>): MviState