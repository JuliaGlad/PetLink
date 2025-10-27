package petlink.android.petlink.ui.calendar.calendar_view.month_view.mapper

import petlink.android.petlink.domain.model.calendar.CalendarEventWithTimeStampDomain
import petlink.android.petlink.ui.calendar.calendar_view.month_view.model.CalendarEventWithTimestampUiModel
import petlink.android.petlink.ui.calendar.calendar_view.month_view.model.ListCalendarEventWithTimestampUi

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
