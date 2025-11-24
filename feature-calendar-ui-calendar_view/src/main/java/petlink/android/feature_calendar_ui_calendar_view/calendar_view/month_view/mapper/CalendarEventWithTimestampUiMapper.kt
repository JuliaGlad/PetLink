package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mapper

import petlink.android.feature_calendar_domain.model.CalendarEventWithTimeStampDomain
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.CalendarEventWithTimestampUiModel
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.ListCalendarEventWithTimestampUi

fun List<CalendarEventWithTimeStampDomain>.toUi() =
    ListCalendarEventWithTimestampUi(
        map {
            it.toUi()
        }.toList()
    )


fun CalendarEventWithTimeStampDomain.toUi() =
    CalendarEventWithTimestampUiModel(
        id = id,
        title = title,
        date = date,
        theme = theme,
        timestamp = timestamp,
        time = time,
        isNotificationOn = isNotificationOn
    )
