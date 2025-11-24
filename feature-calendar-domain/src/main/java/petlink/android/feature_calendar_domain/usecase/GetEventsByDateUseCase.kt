package petlink.android.feature_calendar_domain.usecase

import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel

interface GetEventsByDateUseCase {
    suspend fun invoke(date: String): List<CalendarEventDomainModel>
}