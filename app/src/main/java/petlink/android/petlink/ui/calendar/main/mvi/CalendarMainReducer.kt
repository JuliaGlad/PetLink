package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

class CalendarMainReducer: MviReducer<
        CalendarMainPartialState,
        CalendarMainState> {
    override fun reduce(
        prevState: CalendarMainState,
        partialState: CalendarMainPartialState
    ): CalendarMainState =
        when(partialState){
            is CalendarMainPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is CalendarMainPartialState.Error -> updateError(prevState, partialState.throwable)
        }

    private fun updateDataLoaded(prevState: CalendarMainState, data: ListCalendarEventUiModel) =
        prevState.copy(value = LceState.Content(data))

    private fun updateError(prevState: CalendarMainState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable))
}