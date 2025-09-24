package petlink.android.petlink.ui.calendar.model

class ListCalendarEventUiModel(val events: List<CalendarEventUiModel>)

class CalendarEventUiModel(
    val id: String,
    val title: String,
    val date: String,
    val theme: Int,
    val time: String,
    val isNotificationOn: Boolean
)