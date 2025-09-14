package petlink.android.petlink.domain.usecase.calendar

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import javax.inject.Inject

class UpdateCalendarEventUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend fun invoke(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        isNotificationOn: Boolean
    ){
        repository.updateEvent(
            eventId = eventId,
            title = title,
            date = date,
            theme = theme,
            isNotificationOn = isNotificationOn
        )
    }
}