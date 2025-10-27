package petlink.android.petlink.ui.calendar.history.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

class CalendarEventHistoryReducer: MviReducer<
        CalendarEventHistoryPartialState,
        CalendarEventHistoryState> {
    override fun reduce(
        prevState: CalendarEventHistoryState,
        partialState: CalendarEventHistoryPartialState
    ): CalendarEventHistoryState =
        when(partialState){
            is CalendarEventHistoryPartialState.DataLoaded -> updateDataLoaded(
                prevState = prevState,
                events = partialState.events
            )
            is CalendarEventHistoryPartialState.Error -> updateError(
                prevState = prevState,
                throwable = partialState.throwable
            )
        }

    private fun updateError(prevState: CalendarEventHistoryState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable))

    private fun updateDataLoaded(prevState: CalendarEventHistoryState, events: ListCalendarEventUiModel) =
        prevState.copy(value = LceState.Content(events))
}