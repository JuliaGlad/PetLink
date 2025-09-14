package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import javax.inject.Inject

class DeleteCalendarEventUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend fun invoke(id: String){
        repository.deleteEvent(id = id)
    }
}