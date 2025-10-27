package petlink.android.petlink.data.repository.calendar.dto

import com.google.firebase.Timestamp

class CalendarEventDto(
    val id: String,
    val title: String,
    val date: String,
    val theme: String,
    val timestamp: Timestamp,
    val time: String,
    val isNotificationOn: Boolean
)