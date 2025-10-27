package petlink.android.petlink.ui.calendar.history.fragment.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.GetHistoryEventsUseCase
import javax.inject.Inject

class CalendarEventHistoryLocalDi @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    private val getCaleHistoryEventsUseCase = GetHistoryEventsUseCase(calendarRepository)

    val actor by lazy { CalendarEventHistoryActor(getCaleHistoryEventsUseCase) }

    val reducer by lazy { CalendarEventHistoryReducer() }
}