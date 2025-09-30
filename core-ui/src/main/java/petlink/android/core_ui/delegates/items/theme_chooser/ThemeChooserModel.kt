package petlink.android.core_ui.delegates.items.theme_chooser

import petlink.android.core_ui.custom_view.calendar_event.EventTheme
import kotlin.random.Random

data class ThemeChooserModel(
    val id: Int = Random.nextInt(),
    val items: List<EventTheme>,
    val defaultChosenId: Int = -1
)