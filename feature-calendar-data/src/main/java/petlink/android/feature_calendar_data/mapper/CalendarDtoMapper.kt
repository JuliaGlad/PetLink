package petlink.android.feature_calendar_data.mapper

import com.google.firebase.Timestamp
import petlink.android.feature_calendar_data.dto.CalendarEventDto
import petlink.android.feature_calendar_data.local_source.CalendarEventEntity
import java.text.SimpleDateFormat
import java.util.Locale

fun CalendarEventEntity.toDto() =
    CalendarEventDto(
        id = eventId,
        title = title,
        date = date,
        theme = theme,
        time = time,
        timestamp = Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(dateForTimestamp)!!),
        isNotificationOn = isNotificationOn
    )