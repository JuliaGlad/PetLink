package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_calendar_ui_calendar_view.model.ListCalendarEventUiModel

class DayDataReducer : MviReducer<
        DayDataPartialState,
        DayDataState> {
    override fun reduce(
        prevState: DayDataState,
        partialState: DayDataPartialState
    ): DayDataState =
        when (partialState) {
            is DayDataPartialState.DataLoaded -> updateDataLoaded(
                prevState = prevState,
                data = partialState.data
            )
            is DayDataPartialState.Error -> updateError(
                prevState = prevState,
                throwable = partialState.throwable
            )
        }

    private fun updateDataLoaded(prevState: DayDataState, data: ListCalendarEventUiModel) =
        prevState.copy(value = LceState.Content(data))

    private fun updateError(prevState: DayDataState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable))
}