package petlink.android.petlink.ui.calendar.calendar_view.month_view.recycler_view

import petlink.android.petlink.ui.calendar.calendar_view.month_view.model.CalendarEventWithTimestampUiModel
import kotlin.random.Random

data class CalendarDayModel(
    val id: Int = Random.nextInt(),
    val day: String,
    val events: List<CalendarEventWithTimestampUiModel>,
    val clickListener: (() -> Unit)? = null
)