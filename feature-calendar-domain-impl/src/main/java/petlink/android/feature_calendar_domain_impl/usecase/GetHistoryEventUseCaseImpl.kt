package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel
import petlink.android.feature_calendar_domain.usecase.GetHistoryEventsUseCase
import javax.inject.Inject

class GetHistoryEventUseCaseImpl @Inject constructor(
    private val calendarRepository: CalendarRepository
): GetHistoryEventsUseCase {
    override suspend fun invoke(): List<CalendarEventDomainModel> =
        calendarRepository.getHistoryEvents()
}