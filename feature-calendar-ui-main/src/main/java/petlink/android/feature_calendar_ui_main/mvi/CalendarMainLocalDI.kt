package petlink.android.feature_calendar_ui_main.mvi

import petlink.android.feature_calendar_domain.usecase.GetCalendarEventsUseCase
import javax.inject.Inject

class CalendarMainLocalDI @Inject constructor(
    private val getCalendarEventsUseCase: GetCalendarEventsUseCase
) {
    val actor by lazy { CalendarMainActor(getCalendarEventsUseCase) }

    val reducer by lazy { CalendarMainReducer() }
}