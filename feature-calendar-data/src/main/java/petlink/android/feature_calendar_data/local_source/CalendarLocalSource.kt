package petlink.android.feature_calendar_data.local_source

import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel

interface CalendarLocalSource {

    suspend fun getEvents(
        orderByDate: Boolean = false,
        limit: Long? = null
    ): List<CalendarEventDomainModel>?

    suspend fun deleteEvent(id: String)

    suspend fun deleteEvents()

    suspend fun updateEvents(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )

    suspend fun insertEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )

}