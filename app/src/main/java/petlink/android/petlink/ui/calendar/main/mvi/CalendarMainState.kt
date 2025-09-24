package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

data class CalendarMainState(val value: LceState<ListCalendarEventUiModel>): MviState