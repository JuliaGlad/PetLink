package petlink.android.petlink.domain.mapper.calendar

import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto
import petlink.android.petlink.domain.model.calendar.CalendarEventDomainModel

fun CalendarEventDto.toDomain() =
    CalendarEventDomainModel(
        id = id,
        title = title,
        date = date,
        theme = theme,
        time = time,
        isNotificationOn = isNotificationOn
    )