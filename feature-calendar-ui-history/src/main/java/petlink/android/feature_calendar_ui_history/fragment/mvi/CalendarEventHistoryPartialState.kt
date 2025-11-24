package petlink.android.feature_calendar_ui_history.fragment.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_calendar_ui_history.fragment.model.ListCalendarEventUiModel

sealed interface CalendarEventHistoryPartialState: MviPartialState {

    class Error(val throwable: Throwable): CalendarEventHistoryPartialState

    class DataLoaded(val events: ListCalendarEventUiModel): CalendarEventHistoryPartialState
}