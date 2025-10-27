package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import javax.inject.Inject

class AddEventToHistoryUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend fun invoke(eventId: String){
        calendarRepository.addEventToHistory(eventId)
    }
}