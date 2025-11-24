package petlink.android.feature_calendar_data_impl.local_db

import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabase
import petlink.android.feature_calendar_data.local_source.CalendarEventEntity
import javax.inject.Inject
import kotlin.reflect.KMutableProperty0

class CalendarProvider @Inject constructor(
    private val calendarDatabase: CalendarDatabase
){
    suspend fun getEvents(): List<CalendarEventEntity>? =
        calendarDatabase.calendarDao().getEvents()

    suspend fun insertEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        calendarDatabase.calendarDao().insertEvent(
            CalendarEventEntity(
                eventId = eventId,
                title = title,
                date = date,
                theme = theme,
                time = time,
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
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        val dao = calendarDatabase.calendarDao()
        val events = dao.getEvents()
        for (item in events) {
            if (item.eventId == eventId) {
                with(item) {
                    updateIfChanged(::title, title)
                    updateIfChanged(::date, date)
                    updateIfChanged(::theme, theme)
                    updateIfChanged(::time, time)
                    updateIfChanged(::dateForTimestamp, dateForTimestamp)
                    updateIfChanged(::isNotificationOn, isNotificationOn)
                }
                dao.updateEvent(item)
            }
        }
    }

    suspend fun deleteEvent(eventId: String) {
        val dao = calendarDatabase.calendarDao()
        val events = dao.getEvents()
        for (item in events) {
            if (item.eventId == eventId) {
                dao.deleteEvent(item)
            }
        }
    }

    suspend fun deleteAllEvents() {
        calendarDatabase.calendarDao().deleteAll()
    }

    private fun <T> updateIfChanged(prev: KMutableProperty0<T>, new: T?) {
        if (prev != new) {
            prev.set(new as T)
        }
    }

}