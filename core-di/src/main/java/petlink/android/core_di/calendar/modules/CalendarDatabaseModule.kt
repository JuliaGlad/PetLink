package petlink.android.core_di.calendar.modules

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import petlink.android.core_di.calendar.component.CalendarScope
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabase

@Module
class CalendarDatabaseModule {

    @CalendarScope
    @Provides
    fun provideCalendarDatabase(context: Context): CalendarDatabase =
        databaseBuilder(context, CalendarDatabase::class.java, "calendar_database").build()
}