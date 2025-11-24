package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_calendar_ui_calendar_view.model.ListCalendarEventUiModel

sealed interface DayDataPartialState: MviPartialState {

    class DataLoaded(val data: ListCalendarEventUiModel): DayDataPartialState

    class Error(val throwable: Throwable): DayDataPartialState

}