package petlink.android.petlink.data.mapper.calendar

import petlink.android.petlink.data.local_database.entity.calendar.CalendarEventEntity
import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto

fun CalendarEventEntity.toDto() =
    CalendarEventDto(
        id = eventId,
        title = title,
        date = date,
        theme = theme,
        time = time,
        isNotificationOn = isNotificationOn
    )