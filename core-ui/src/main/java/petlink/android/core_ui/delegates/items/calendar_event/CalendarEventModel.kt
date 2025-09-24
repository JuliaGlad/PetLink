package petlink.android.core_ui.delegates.items.calendar_event

import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import kotlin.random.Random

data class CalendarEventModel(
    val id: Int = Random.nextInt(),
    val eventId: String,
    val title: String,
    val theme: CalendarEventTheme,
    val time: String,
    val eventDate: String,
    val isNotificationOn: Boolean,
    val clickListener: () -> Unit
)