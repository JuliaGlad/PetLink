package petlink.android.feature_calendar_data_impl.local_source

import petlink.android.feature_calendar_data.local_source.CalendarLocalSource
import petlink.android.feature_calendar_data.mapper.toDomain
import petlink.android.feature_calendar_data.mapper.toDto
import petlink.android.feature_calendar_data_impl.local_db.CalendarProvider
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabase
import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel
import java.time.LocalDate
import javax.inject.Inject
import kotlin.collections.asSequence
import kotlin.collections.map

class CalendarLocalSourceImpl @Inject constructor(
    private val calendarDatabase: CalendarDatabase
): CalendarLocalSource {
    override suspend fun getEvents(
        orderByDate: Boolean,
        limit: Long?
    ): List<CalendarEventDomainModel>? {
        val events = CalendarProvider(calendarDatabase).getEvents()
        if (events == null) return null
        return if (orderByDate) {
            val sorted = events.asSequence()
                .mapNotNull { event ->
                    runCatching { LocalDate.parse(event.date) to event }.getOrNull()
                }
                .sortedBy { (eventDate, _) -> eventDate }

            (if (limit != null) sorted.take(limit.toInt()) else sorted)
                .map { (_, event) -> event.toDto().toDomain() }
                .toList()
        } else {
            events.map { it.toDto().toDomain() }
        }
    }

    override suspend fun deleteEvent(id: String) {
        CalendarProvider(calendarDatabase).deleteEvent(id)
    }

    override suspend fun deleteEvents() {
        CalendarProvider(calendarDatabase).deleteAllEvents()
    }

    override suspend fun updateEvents(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        CalendarProvider(calendarDatabase).updateEvent(
            eventId = eventId,
            title = title,
            date = date,
            theme = theme,
            time = time,
            dateForTimestamp = dateForTimestamp,
            isNotificationOn = isNotificationOn
        )
    }

    override suspend fun insertEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        CalendarProvider(calendarDatabase).insertEvent(
            eventId = eventId,
            title = title,
            date = date,
            theme = theme,
            time = time,
            dateForTimestamp = dateForTimestamp,
            isNotificationOn = isNotificationOn
        )
    }
}