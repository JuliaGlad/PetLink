package petlink.android.petlink.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import petlink.android.petlink.data.local_database.converter.OwnerLocalDbConverter
import petlink.android.petlink.data.local_database.converter.PetLocalDbConverter
import petlink.android.petlink.data.local_database.dao.CalendarDao
import petlink.android.petlink.data.local_database.dao.UserDao
import petlink.android.petlink.data.local_database.entity.calendar.CalendarEventEntity
import petlink.android.petlink.data.local_database.entity.user.UserEntity

@TypeConverters(
    value = [
        OwnerLocalDbConverter::class,
        PetLocalDbConverter::class
    ]
)
@Database(
    entities = [
        UserEntity::class,
        CalendarEventEntity::class
    ], version = 1
)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun calendarDao(): CalendarDao
}