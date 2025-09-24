package petlink.android.petlink.ui.calendar.main.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.GetCalendarEventsUseCase
import javax.inject.Inject

class CalendarMainLocalDI @Inject constructor(
    private val repository: CalendarRepository
) {
    private val getCalendarEventsUseCase = GetCalendarEventsUseCase(repository)

    val actor by lazy { CalendarMainActor(getCalendarEventsUseCase) }

    val reducer by lazy { CalendarMainReducer() }
}