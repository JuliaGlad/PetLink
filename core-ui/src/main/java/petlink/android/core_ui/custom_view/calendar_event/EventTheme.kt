package petlink.android.core_ui.custom_view.calendar_event

import androidx.annotation.ColorRes
import petlink.android.core_ui.R

class EventTheme(
    @ColorRes val background: Int,
    @ColorRes val iconTint: Int,
    @ColorRes val titleTextColor: Int,
    @ColorRes val descriptionTextColor: Int
)

enum class CalendarEventTheme(val theme: EventTheme) {
    GREEN(
        EventTheme(
            background = R.color.light_green_variant,
            iconTint = R.color.icon_color,
            titleTextColor = R.color.black,
            descriptionTextColor = R.color.icon_color
        )
    ),
    BLUE(
        EventTheme(
            background = R.color.light_turquoise,
            iconTint = R.color.medium_turquoise,
            titleTextColor = R.color.dark_turquoise,
            descriptionTextColor = R.color.medium_turquoise
        )
    ),
    PURPLE(
        EventTheme(
            background = R.color.light_purple,
            iconTint = R.color.medium_purple,
            titleTextColor = R.color.dark_purple,
            descriptionTextColor = R.color.medium_purple
        )
    ),
    YELLOW(
        EventTheme(
            background = R.color.light_yellow,
            iconTint = R.color.medium_yellow,
            titleTextColor = R.color.dark_yellow,
            descriptionTextColor = R.color.medium_yellow
        )
    ),
    PINK(
        EventTheme(
            background = R.color.light_pink,
            iconTint = R.color.medium_pink,
            titleTextColor = R.color.dark_pink,
            descriptionTextColor = R.color.medium_pink
        )
    ),
    CORAL(
        EventTheme(
            background = R.color.light_coral,
            iconTint = R.color.medium_coral,
            titleTextColor = R.color.dark_coral,
            descriptionTextColor = R.color.medium_coral
        )
    ),
    ORANGE(
        EventTheme(
            background = R.color.light_coral,
            iconTint = R.color.medium_orange,
            titleTextColor = R.color.dark_orange,
            descriptionTextColor = R.color.medium_orange
        )
    )
}