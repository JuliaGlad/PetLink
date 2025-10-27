package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.mapper.calendar.toDomain
import petlink.android.petlink.domain.model.calendar.CalendarEventDomainModel
import java.util.stream.Collectors
import javax.inject.Inject

class GetCalendarEventsUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend fun invoke(
        orderByDate: Boolean = false,
        limit: Long? = null
    ): List<CalendarEventDomainModel> =
        repository.getEvents(
            orderByDate = orderByDate,
            limit = limit
        ).map { it.toDomain() }.toList()
}