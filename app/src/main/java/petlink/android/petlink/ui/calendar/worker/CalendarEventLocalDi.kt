package petlink.android.petlink.ui.calendar.worker

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.AddEventToHistoryUseCase
import javax.inject.Inject

class CalendarEventLocalDi @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    val addEventToHistoryUseCase = AddEventToHistoryUseCase(calendarRepository)
}