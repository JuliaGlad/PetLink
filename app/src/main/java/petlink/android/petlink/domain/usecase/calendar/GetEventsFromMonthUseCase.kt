package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.mapper.calendar.toTimeStampDomain
import petlink.android.petlink.domain.model.calendar.CalendarEventWithTimeStampDomain
import javax.inject.Inject

class GetEventsFromMonthUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend fun invoke(
        year: Int,
        month: Int
    ): List<CalendarEventWithTimeStampDomain> =
        repository.getEventsFromMonth(year, month)
            .map { it.toTimeStampDomain() }
            .toList()
}