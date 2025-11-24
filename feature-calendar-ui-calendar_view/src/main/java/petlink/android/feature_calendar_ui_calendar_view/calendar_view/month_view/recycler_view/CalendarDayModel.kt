package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.recycler_view

import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.CalendarEventWithTimestampUiModel
import kotlin.random.Random

data class CalendarDayModel(
    val id: Int = Random.nextInt(),
    val day: String,
    var events: List<CalendarEventWithTimestampUiModel>,
    val clickListener: (() -> Unit)? = null
)