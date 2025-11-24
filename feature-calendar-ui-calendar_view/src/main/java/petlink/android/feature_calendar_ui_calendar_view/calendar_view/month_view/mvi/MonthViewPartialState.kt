package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.ListCalendarEventWithTimestampUi

sealed interface MonthViewPartialState: MviPartialState {

    class DataLoaded(val data: ListCalendarEventWithTimestampUi): MonthViewPartialState

    class Error(val throwable: Throwable): MonthViewPartialState

}