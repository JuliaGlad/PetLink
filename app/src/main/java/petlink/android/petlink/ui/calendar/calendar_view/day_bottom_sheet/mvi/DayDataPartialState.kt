package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

sealed interface DayDataPartialState: MviPartialState {

    class DataLoaded(val data: ListCalendarEventUiModel): DayDataPartialState

    class Error(val throwable: Throwable): DayDataPartialState

}