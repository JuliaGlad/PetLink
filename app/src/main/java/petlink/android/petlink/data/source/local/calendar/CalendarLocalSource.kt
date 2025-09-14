package petlink.android.petlink.data.source.local.calendar

import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto

interface CalendarLocalSource {

    suspend fun getEvents(
        orderByDate: Boolean = false,
        limit: Int? = null
    ): List<CalendarEventDto>?

    suspend fun deleteEvent(id: String)

    suspend fun deleteEvents()

    suspend fun updateEvents(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )

    suspend fun insertEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )

}