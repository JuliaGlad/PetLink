package petlink.android.petlink.ui.calendar.history.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

data class CalendarEventHistoryState(val value: LceState<ListCalendarEventUiModel>): MviState