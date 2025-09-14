package petlink.android.core_ui.delegates.items.calendar_event

import kotlin.random.Random

data class CalendarEventModel(
    val id: String,
    val title: String,
    val eventDate: String,
    val isNotificationOn: Boolean
)