package petlink.android.petlink.data.repository.calendar

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: CalendarLocalSource
) : CalendarRepository {
    override suspend fun addEvent(
        title: String,
        date: String,
        theme: String,
        dateForTimestamp: String,
        time: String,
        isNotificationOn: Boolean
    ) {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val parsedDate: Date = sdf.parse(dateForTimestamp)!!

        auth.currentUser?.uid?.let { uid ->
            val event = hashMapOf(
                EVENT_TITLE to title,
                EVENT_DATE to date,
                EVENT_TIME to time,
                EVENT_THEME to theme,
                EVENT_DATE_TIME_STAMP to Timestamp(parsedDate),
                IS_NOTIFICATION_ON to isNotificationOn
            )
            store.collection(USER_COLLECTION)
                .document(uid)
                .collection(CALENDAR_EVENT)
                .document()
                .set(event)
                .await()
        }
    }

    override suspend fun getEvents(
        orderByDate: Boolean,
        limit: Long?
    ): List<CalendarEventDto> {
        val local = localSource.getEvents(orderByDate, limit)
        return if (local.isNullOrEmpty()) {
            val snapshot = auth.currentUser?.uid?.let { uid ->
                var query = store.collection(USER_COLLECTION)
                    .document(uid)
                    .collection(CALENDAR_EVENT) as Query

                if (orderByDate) query = query.orderBy(EVENT_DATE_TIME_STAMP)
                if (limit != null) query = query.limit(limit)

                query.get().await()
            }
            snapshot?.documents?.map { document ->
                CalendarEventDto(
                    id = document.id,
                    title = document.getString(EVENT_TITLE).toString(),
                    date = document.get(EVENT_DATE).toString(),
                    theme = document.get(EVENT_THEME).toString(),
                    time = document.get(EVENT_TIME).toString(),
                    isNotificationOn = document.getBoolean(IS_NOTIFICATION_ON) == true
                )
            }?.toList() ?: emptyList()
        } else local
    }

    override suspend fun deleteEvent(id: String) {
        auth.currentUser?.uid?.let { uid ->
            store.collection(USER_COLLECTION)
                .document(uid)
                .collection(CALENDAR_EVENT)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun updateEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) {
        auth.currentUser?.uid?.let { uid ->
            updateEventDataFields(
                uid,
                eventId,
                updates = mapOf(
                    EVENT_TITLE to title,
                    EVENT_DATE to date,
                    EVENT_THEME to theme,
                    IS_NOTIFICATION_ON to isNotificationOn
                )
            )
        }
    }

    private suspend fun updateEventDataFields(
        userId: String,
        eventId: String,
        updates: Map<String, Any?>
    ) {
        val filteredUpdates = updates.filterValues { it != null }.mapValues { it.value!! }

        store.collection(USER_COLLECTION)
            .document(userId)
            .collection(CALENDAR_EVENT)
            .document(eventId)
            .update(filteredUpdates)
            .await()
    }

    companion object {
        const val EVENT_TIME = "EventTime"
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm"
        const val CALENDAR_EVENT = "CalendarEvent"
        const val USER_COLLECTION = "Users"
        const val EVENT_TITLE = "EventTitle"
        const val EVENT_THEME = "EventTheme"
        const val EVENT_DATE_TIME_STAMP = "EvenDateTimeStamp"
        const val EVENT_DATE = "EventDate"
        const val IS_NOTIFICATION_ON = "IsNotificationOn"
    }
}