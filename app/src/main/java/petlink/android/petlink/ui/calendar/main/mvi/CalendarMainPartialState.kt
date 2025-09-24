package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

sealed interface CalendarMainPartialState: MviPartialState {

    class Error(val throwable: Throwable): CalendarMainPartialState

    class DataLoaded(val data: ListCalendarEventUiModel): CalendarMainPartialState

}