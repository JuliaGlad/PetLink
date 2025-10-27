package petlink.android.petlink.ui.calendar.calendar_view.month_view.model

import com.google.firebase.Timestamp

class CalendarEventWithTimestampUiModel(
    val id: String,
    val title: String,
    val date: String,
    val theme: String,
    val timestamp: Timestamp,
    val time: String,
    val isNotificationOn: Boolean
)