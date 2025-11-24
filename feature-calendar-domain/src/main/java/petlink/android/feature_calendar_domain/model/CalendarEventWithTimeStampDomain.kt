package petlink.android.feature_calendar_domain.model

import com.google.firebase.Timestamp

class CalendarEventWithTimeStampDomain(
    val id: String,
    val title: String,
    val date: String,
    val theme: String,
    val timestamp: Timestamp,
    val time: String,
    val isNotificationOn: Boolean
)