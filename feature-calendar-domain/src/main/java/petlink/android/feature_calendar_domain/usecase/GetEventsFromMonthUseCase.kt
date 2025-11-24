package petlink.android.feature_calendar_domain.usecase

import petlink.android.feature_calendar_domain.model.CalendarEventWithTimeStampDomain

interface GetEventsFromMonthUseCase {
    suspend fun invoke(
        year: Int,
        month: Int
    ): List<CalendarEventWithTimeStampDomain>
}