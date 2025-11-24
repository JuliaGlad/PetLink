package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi

import petlink.android.feature_calendar_domain.usecase.GetEventsFromMonthUseCase
import javax.inject.Inject

class MonthViewLocalDI @Inject constructor(
    getEventsFromMonthUseCase: GetEventsFromMonthUseCase
) {
    val actor by lazy { MonthViewActor(getEventsFromMonthUseCase) }

    val reducer by lazy { MonthViewReducer() }
}