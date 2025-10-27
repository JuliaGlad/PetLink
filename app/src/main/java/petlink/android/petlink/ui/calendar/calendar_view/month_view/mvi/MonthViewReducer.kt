package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.petlink.ui.calendar.calendar_view.month_view.model.ListCalendarEventWithTimestampUi

class MonthViewReducer: MviReducer<MonthViewPartialState, MonthViewState> {
    override fun reduce(
        prevState: MonthViewState,
        partialState: MonthViewPartialState
    ): MonthViewState =
        when(partialState){
            is MonthViewPartialState.DataLoaded -> updateDataLoaded(
                prevState = prevState,
                data = partialState.data
            )
            is MonthViewPartialState.Error -> updateError(
                prevState = prevState,
                throwable = partialState.throwable
            )
        }

    private fun updateDataLoaded(prevState: MonthViewState, data: ListCalendarEventWithTimestampUi) =
        prevState.copy(value = LceState.Content(data))

    private fun updateError(prevState: MonthViewState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable))
}