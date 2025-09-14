package petlink.android.petlink.data.local_database.provider.calendar

import petlink.android.petlink.App
import petlink.android.petlink.data.local_database.entity.calendar.CalendarEventEntity
import kotlin.reflect.KMutableProperty0

class CalendarProvider {
    suspend fun getEvents(): List<CalendarEventEntity>? =
        App.Companion.app.database.calendarDao().getEvents()

    suspend fun insertEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        App.Companion.app.database.calendarDao().insertEvent(
            CalendarEventEntity(
                eventId = eventId,
                title = title,
                date = date,
                theme = theme,
                dateForTimestamp = dateForTimestamp,
                isNotificationOn = isNotificationOn
            )
        )
    }

    suspend fun updateEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        val dao = App.Companion.app.database.calendarDao()
        val events = dao.getEvents()
        for (item in events) {
            if (item.eventId == eventId) {
                with(item) {
                    updateIfChanged(::title, title)
                    updateIfChanged(::date, date)
                    updateIfChanged(::theme, theme)
                    updateIfChanged(::dateForTimestamp, dateForTimestamp)
                    updateIfChanged(::isNotificationOn, isNotificationOn)
                }
                dao.updateEvent(item)
            }
        }
    }

    suspend fun deleteEvent(eventId: String) {
        val dao = App.Companion.app.database.calendarDao()
        val events = dao.getEvents()
        for (item in events) {
            if (item.eventId == eventId) {
                dao.deleteEvent(item)
            }
        }
    }

    suspend fun deleteAllEvents() {
        App.Companion.app.database.calendarDao().deleteAll()
    }

    private fun <T> updateIfChanged(prev: KMutableProperty0<T>, new: T?) {
        if (prev != new) {
            prev.set(new as T)
        }
    }

}
