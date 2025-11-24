package petlink.android.feature_calendar_domain.model

class CalendarEventDomainModel(
    val id: String,
    val title: String,
    val date: String,
    val theme: String,
    val time: String,
    val isNotificationOn: Boolean
)