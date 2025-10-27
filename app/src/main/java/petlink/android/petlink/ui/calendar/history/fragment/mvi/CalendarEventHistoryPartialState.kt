package petlink.android.petlink.ui.calendar.history.fragment.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

sealed interface CalendarEventHistoryPartialState: MviPartialState {

    class Error(val throwable: Throwable): CalendarEventHistoryPartialState

    class DataLoaded(val events: ListCalendarEventUiModel): CalendarEventHistoryPartialState
}