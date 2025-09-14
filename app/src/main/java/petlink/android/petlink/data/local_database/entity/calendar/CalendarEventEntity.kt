package petlink.android.petlink.data.local_database.entity.calendar

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
    var dateForTimestamp: String,
    var theme: String,
    var isNotificationOn: Boolean
)