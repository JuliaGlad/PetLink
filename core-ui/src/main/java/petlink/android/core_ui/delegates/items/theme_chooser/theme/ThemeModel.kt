package petlink.android.core_ui.delegates.items.theme_chooser.theme

import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.core_ui.custom_view.calendar_event.EventTheme
import kotlin.random.Random

data class ThemeModel(
    val id: Int = Random.nextInt(),
    val theme: CalendarEventTheme,
    var isChosen: Boolean,
    val clickListener: () -> Unit
)