package petlink.android.petlink.data.repository.calendar

import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto

interface CalendarRepository {

    suspend fun addEvent(
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )

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
        isNotificationOn: Boolean
    )

}