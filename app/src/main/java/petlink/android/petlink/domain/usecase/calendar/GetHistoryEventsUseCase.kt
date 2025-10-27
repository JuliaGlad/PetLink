package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.mapper.calendar.toDomain
import petlink.android.petlink.domain.model.calendar.CalendarEventDomainModel
import javax.inject.Inject

class GetHistoryEventsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend fun invoke(): List<CalendarEventDomainModel> =
        calendarRepository.getHistoryEvents()
            .map { it.toDomain() }
            .toList()
}