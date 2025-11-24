package petlink.android.feature_calendar_domain.usecase

import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel

interface GetCalendarEventsUseCase {
    suspend fun invoke(
        orderByDate: Boolean = false,
        limit: Long? = null
    ): List<CalendarEventDomainModel>
}