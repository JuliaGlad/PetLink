package petlink.android.feature_calendar_data_impl.local_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import petlink.android.feature_calendar_data.local_source.CalendarEventEntity

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar_events")
    suspend fun getEvents(): List<CalendarEventEntity>

    @Insert
    suspend fun insertEvent(event: CalendarEventEntity)

    @Update
    suspend fun updateEvent(event: CalendarEventEntity)

    @Query("DELETE FROM calendar_events")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteEvent(event: CalendarEventEntity)
}