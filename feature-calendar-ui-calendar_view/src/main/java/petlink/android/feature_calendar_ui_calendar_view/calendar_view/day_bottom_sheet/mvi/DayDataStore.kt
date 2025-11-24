package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class DayDataStore(
    actor: DayDataActor,
    reducer: DayDataReducer
) : MviStore<
        DayDataPartialState,
        DayDataIntent,
        DayDataState,
        DayDataEffect>(
    reducer = reducer,
    actor = actor
) {
    override fun initialStateCreator(): DayDataState = DayDataState(LceState.Loading)
}