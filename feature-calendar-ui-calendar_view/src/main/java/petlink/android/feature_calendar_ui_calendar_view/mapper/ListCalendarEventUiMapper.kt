package petlink.android.feature_calendar_ui_calendar_view.mapper

import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel
import petlink.android.feature_calendar_ui_calendar_view.model.CalendarEventUiModel
import petlink.android.feature_calendar_ui_calendar_view.model.ListCalendarEventUiModel

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
        time = time,
        isNotificationOn = isNotificationOn
    )