package petlink.android.feature_calendar_ui_main.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_calendar_ui_main.model.ListCalendarEventUiModel

sealed interface CalendarMainPartialState: MviPartialState {

    class Error(val throwable: Throwable): CalendarMainPartialState

    class DataLoaded(val data: ListCalendarEventUiModel): CalendarMainPartialState

}