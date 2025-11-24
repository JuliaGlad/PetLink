package petlink.android.feature_calendar_data.local_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "calendar_events"
)
class CalendarEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val eventId: String,
    var title: String,
    var date: String,
    var time: String,
    var dateForTimestamp: String,
    var theme: String,
    var isNotificationOn: Boolean
)