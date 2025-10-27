package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.GetEventsFromMonthUseCase
import javax.inject.Inject

class MonthViewLocalDI @Inject constructor(
    calendarRepository: CalendarRepository
) {
    private val getEventsFromMonthUseCase = GetEventsFromMonthUseCase(calendarRepository)

    val actor by lazy { MonthViewActor(getEventsFromMonthUseCase) }

    val reducer by lazy { MonthViewReducer() }
}