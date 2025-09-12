package petlink.android.petlink.data.repository.calendar

import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto

interface CalendarRepository {

    suspend fun addEvent(
        title: String,
        date: String,
        theme: String,
        isNotificationOn: Boolean
    )

    suspend fun getEvents(): List<CalendarEventDto>

    suspend fun deleteEvent(id: String)

    suspend fun updateEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        isNotificationOn: Boolean
    )

}