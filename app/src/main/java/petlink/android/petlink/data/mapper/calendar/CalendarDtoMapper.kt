package petlink.android.petlink.data.mapper.calendar

import com.google.firebase.Timestamp
import petlink.android.petlink.data.local_database.entity.calendar.CalendarEventEntity
import petlink.android.petlink.data.repository.calendar.CalendarRepositoryImpl.Companion.DATE_FORMAT
import petlink.android.petlink.data.repository.calendar.dto.CalendarEventDto
import java.text.SimpleDateFormat
import java.util.Locale

fun CalendarEventEntity.toDto() =
    CalendarEventDto(
        id = eventId,
        title = title,
        date = date,
        theme = theme,
        time = time,
        timestamp = Timestamp(SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(dateForTimestamp)!!),
        isNotificationOn = isNotificationOn
    )