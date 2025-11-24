package petlink.android.feature_calendar_data.mapper

import petlink.android.feature_calendar_data.dto.CalendarEventDto
import petlink.android.feature_calendar_domain.model.CalendarEventDomainModel

fun CalendarEventDto.toDomain() =
    CalendarEventDomainModel(
        id = id,
        title = title,
        date = date,
        theme = theme,
        time = time,
        isNotificationOn = isNotificationOn
    )