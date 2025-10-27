package petlink.android.petlink.domain.usecase.calendar

import android.util.Log
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import javax.inject.Inject

class AddCalendarEventUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend fun invoke(
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ): String = repository.addEvent(
        title = title,
        date = date,
        theme = theme,
        time = time,
        dateForTimestamp = dateForTimestamp,
        isNotificationOn = isNotificationOn
    )
}