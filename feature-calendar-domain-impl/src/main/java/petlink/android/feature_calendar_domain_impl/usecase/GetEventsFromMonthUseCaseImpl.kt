package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.model.CalendarEventWithTimeStampDomain
import petlink.android.feature_calendar_domain.usecase.GetEventsFromMonthUseCase
import javax.inject.Inject

class GetEventsFromMonthUseCaseImpl @Inject constructor(
    private val repository: CalendarRepository
): GetEventsFromMonthUseCase {
    override suspend fun invoke(
        year: Int,
        month: Int
    ): List<CalendarEventWithTimeStampDomain> =
        repository.getEventsFromMonth(year, month)
}