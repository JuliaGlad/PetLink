package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.usecase.UpdateCalendarEventUseCase
import javax.inject.Inject

class UpdateCalendarEventUseCaseImpl @Inject constructor(
    private val repository: CalendarRepository
): UpdateCalendarEventUseCase {
    override suspend fun invoke(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ){
        repository.updateEvent(
            eventId = eventId,
            title = title,
            date = date,
            theme = theme,
            dateForTimestamp = dateForTimestamp,
            time = time,
            isNotificationOn = isNotificationOn
        )
    }
}