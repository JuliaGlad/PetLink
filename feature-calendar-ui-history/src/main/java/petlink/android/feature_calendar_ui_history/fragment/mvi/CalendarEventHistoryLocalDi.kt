package petlink.android.feature_calendar_ui_history.fragment.mvi

import petlink.android.feature_calendar_domain.usecase.GetHistoryEventsUseCase
import javax.inject.Inject

class CalendarEventHistoryLocalDi @Inject constructor(
    private val getCalendarHistoryEventsUseCase: GetHistoryEventsUseCase
) {
    val actor by lazy { CalendarEventHistoryActor(getCalendarHistoryEventsUseCase) }

    val reducer by lazy { CalendarEventHistoryReducer() }
}