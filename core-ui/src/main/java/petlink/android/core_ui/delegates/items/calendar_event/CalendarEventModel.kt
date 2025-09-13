package petlink.android.core_ui.delegates.items.calendar_event

import kotlin.random.Random

data class CalendarEventModel(
    val id: Int = Random.nextInt(),
    val title: String,
    val eventDate: String,
    val isNotificationOn: Boolean,
    val clickListener: () -> Unit
)