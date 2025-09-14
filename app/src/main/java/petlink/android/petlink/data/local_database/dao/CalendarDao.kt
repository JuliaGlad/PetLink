package petlink.android.petlink.data.local_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import petlink.android.petlink.data.local_database.entity.calendar.CalendarEventEntity

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