package petlink.android.feature_calendar_data.repository

import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel
import petlink.android.feature_calendar_domain.model.CalendarEventWithTimeStampDomain

interface CalendarRepository {

    suspend fun getEventsByDate(
        date: String
    ): List<CalendarEventDomainModel>

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
    ): List<CalendarEventWithTimeStampDomain>

    suspend fun getEvents(
        orderByDate: Boolean = false,
        limit: Long? = null
    ): List<CalendarEventDomainModel>

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

    suspend fun getHistoryEvents(): List<CalendarEventDomainModel>

}