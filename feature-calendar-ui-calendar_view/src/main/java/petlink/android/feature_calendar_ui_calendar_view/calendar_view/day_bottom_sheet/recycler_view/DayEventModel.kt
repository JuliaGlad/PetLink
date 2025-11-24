package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.recycler_view

import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import kotlin.random.Random

data class DayEventModel(
    val id: Int = Random.nextInt(),
    val eventId: String,
    var title: String,
    var theme: CalendarEventTheme,
    var time: String,
    var eventDate: String,
    var isNotificationOn: Boolean,
    val clickListener: () -> Unit
)