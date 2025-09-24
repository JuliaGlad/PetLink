package petlink.android.petlink.ui.calendar.mapper

import petlink.android.petlink.domain.model.calendar.CalendarEventDomainModel
import petlink.android.petlink.ui.calendar.model.CalendarEventUiModel
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel

fun List<CalendarEventDomainModel>.toUi()=
    ListCalendarEventUiModel(
        events = map {
            it.toUi()
        }.toList()
    )

fun CalendarEventDomainModel.toUi() =
    CalendarEventUiModel(
        id = id,
        title = title,
        date = date,
        theme = theme.toInt(),
        time = theme,
        isNotificationOn = isNotificationOn
    )