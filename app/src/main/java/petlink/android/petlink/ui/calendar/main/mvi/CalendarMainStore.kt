package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class CalendarMainStore(
    actor: CalendarMainActor,
    reducer: CalendarMainReducer
): MviStore<
        CalendarMainPartialState,
        CalendarMainIntent,
        CalendarMainState,
        CalendarMainEffect>(
            actor = actor,
            reducer = reducer
        ) {
    override fun initialStateCreator(): CalendarMainState = CalendarMainState(LceState.Loading)
}