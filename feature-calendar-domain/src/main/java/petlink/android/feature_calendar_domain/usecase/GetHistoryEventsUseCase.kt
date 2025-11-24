package petlink.android.feature_calendar_domain.usecase

import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel

interface GetHistoryEventsUseCase {
    suspend fun invoke(): List<CalendarEventDomainModel>
}