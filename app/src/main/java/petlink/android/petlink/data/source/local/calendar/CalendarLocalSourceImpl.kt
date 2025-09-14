package petlink.android.petlink.data.source.local.calendar

import petlink.android.petlink.data.local_database.provider.calendar.CalendarProvider
import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto
import petlink.android.petlink.data.source.local.mapper.calendar.toDto
import java.time.LocalDate
import java.util.stream.Collectors

class CalendarLocalSourceImpl : CalendarLocalSource {
    override suspend fun getEvents(
        orderByDate: Boolean,
        limit: Int?
    ): List<CalendarEventDto>? {
        val events = CalendarProvider().getEvents()
        if (events == null) return null
        return if (orderByDate) {
            val sorted = events.asSequence()
                .mapNotNull { event ->
                    runCatching { LocalDate.parse(event.date) to event }.getOrNull()
                }
                .sortedBy { (eventDate, _) -> eventDate }

            (if (limit != null) sorted.take(limit) else sorted)
                .map { (_, event) -> event.toDto() }
                .toList()
        } else {
            events.map { it.toDto() }
        }
    }

    override suspend fun deleteEvent(id: String) {
        CalendarProvider().deleteEvent(id)
    }

    override suspend fun deleteEvents() {
        CalendarProvider().deleteAllEvents()
    }

    override suspend fun updateEvents(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        CalendarProvider().updateEvent(
            eventId = eventId,
            title = title,
            date = date,
            theme = theme,
            dateForTimestamp = dateForTimestamp,
            isNotificationOn = isNotificationOn
        )
    }

    override suspend fun insertEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        CalendarProvider().insertEvent(
            eventId = eventId,
            title = title,
            date = date,
            theme = theme,
            dateForTimestamp = dateForTimestamp,
            isNotificationOn = isNotificationOn
        )
    }
}