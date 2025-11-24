package petlink.android.feature_calendar_data_impl.local_db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import petlink.android.feature_calendar_data_impl.local_db.CalendarDao
import petlink.android.feature_calendar_data.local_source.CalendarEventEntity

@Database(
    entities = [CalendarEventEntity::class],
    version = 1
)
abstract class CalendarDatabase: RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
}