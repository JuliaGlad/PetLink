package petlink.android.feature_calendar_data_impl.local_db.db

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CalendarDatabaseModule {

        @Provides
        fun provideCalendarDatabase(context: Context): CalendarDatabase =
            databaseBuilder(context, CalendarDatabase::class.java, "calendar_database").build()
}