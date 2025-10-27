package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

data class DayDataState(val value: LceState<ListCalendarEventUiModel>): MviState