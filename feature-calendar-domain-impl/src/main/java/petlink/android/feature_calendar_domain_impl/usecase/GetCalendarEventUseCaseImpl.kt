package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel
import petlink.android.feature_calendar_domain.usecase.GetCalendarEventsUseCase
import javax.inject.Inject

class GetCalendarEventUseCaseImpl @Inject constructor(
    private val repository: CalendarRepository
): GetCalendarEventsUseCase {
    override suspend fun invoke(
        orderByDate: Boolean,
        limit: Long?
    ): List<CalendarEventDomainModel> =
        repository.getEvents(
            orderByDate = orderByDate,
            limit = limit
        )
}