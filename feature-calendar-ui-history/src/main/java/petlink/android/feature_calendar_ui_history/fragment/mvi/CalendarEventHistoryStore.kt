package petlink.android.feature_calendar_ui_history.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class CalendarEventHistoryStore(
    reducer: CalendarEventHistoryReducer,
    actor: CalendarEventHistoryActor
): MviStore<
        CalendarEventHistoryPartialState,
        CalendarEventHistoryIntent,
        CalendarEventHistoryState,
        CalendarEventHistoryEffect>(
            actor = actor,
            reducer = reducer
        ) {
    override fun initialStateCreator(): CalendarEventHistoryState = CalendarEventHistoryState(value = LceState.Loading)
}