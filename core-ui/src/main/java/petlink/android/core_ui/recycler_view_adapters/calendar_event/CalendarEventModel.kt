package petlink.android.core_ui.recycler_view_adapters.calendar_event

import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import kotlin.random.Random

data class CalendarEventModel(
    val id: Int = Random.nextInt(),
    var title: String,
    var theme: CalendarEventTheme,
    var time: String,
    var eventDate: String,
    var isNotificationOn: Boolean,
    val clickListener: (() -> Unit)? = null
)