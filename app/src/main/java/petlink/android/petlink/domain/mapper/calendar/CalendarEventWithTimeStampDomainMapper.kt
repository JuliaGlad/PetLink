package petlink.android.petlink.domain.mapper.calendar

import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto
import petlink.android.petlink.domain.model.calendar.CalendarEventDomainModel
import petlink.android.petlink.domain.model.calendar.CalendarEventWithTimeStampDomain

fun CalendarEventDto.toTimeStampDomain() =
    CalendarEventWithTimeStampDomain(
        id = id,
        title = title,
        date = date,
        theme = theme,
        time = time,
        timestamp = timestamp,
        isNotificationOn = isNotificationOn
    )