package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.mapper.calendar.toDomain
import petlink.android.petlink.domain.model.calendar.CalendarEventDomainModel
import javax.inject.Inject

class GetEventsByDateUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend fun invoke(date: String): List<CalendarEventDomainModel> =
        repository.getEventsByDate(date)
            .map { it.toDomain() }
            .toList()
}