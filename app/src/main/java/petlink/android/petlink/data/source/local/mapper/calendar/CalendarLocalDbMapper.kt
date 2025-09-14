package petlink.android.petlink.data.source.local.mapper.calendar

import petlink.android.petlink.data.local_database.entity.calendar.CalendarEventEntity
import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto

fun CalendarEventEntity.toDto() =
    CalendarEventDto(
        id = eventId,
        title = title,
        theme = theme,
        date = date,
        isNotificationOn = isNotificationOn
    )