package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class MonthViewStore(
    actor: MonthViewActor,
    reducer: MonthViewReducer
) : MviStore<MonthViewPartialState,
        MonthViewIntent,
        MonthViewState,
        MonthViewEffect>(
    reducer = reducer,
    actor = actor
) {
    override fun initialStateCreator(): MonthViewState = MonthViewState(value = LceState.Loading)
}