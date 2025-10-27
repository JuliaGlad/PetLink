package petlink.android.petlink.data.repository.calendar

import com.google.firebase.Timestamp
import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto

interface CalendarRepository {

    suspend fun getEventsByDate(
        date: String
    ): List<CalendarEventDto>

    suspend fun addEvent(
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ): String

    suspend fun getEventsFromMonth(
        year: Int,
        month: Int
    ): List<CalendarEventDto>

    suspend fun getEvents(
        orderByDate: Boolean = false,
        limit: Long? = null
    ): List<CalendarEventDto>

    suspend fun deleteEvent(id: String)

    suspend fun updateEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )

    suspend fun addEventToHistory(
        eventId: String
    )

    suspend fun getHistoryEvents(): List<CalendarEventDto>

}