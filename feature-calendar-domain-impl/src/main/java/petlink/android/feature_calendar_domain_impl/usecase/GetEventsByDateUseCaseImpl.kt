package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel
import petlink.android.feature_calendar_domain.usecase.GetEventsByDateUseCase
import javax.inject.Inject

class GetEventsByDateUseCaseImpl @Inject constructor(
    private val repository: CalendarRepository
): GetEventsByDateUseCase {
    override suspend fun invoke(date: String): List<CalendarEventDomainModel> =
        repository.getEventsByDate(date)
}