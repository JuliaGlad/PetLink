package petlink.android.core_di.calendar.modules

import dagger.Binds
import dagger.Module
import dagger.Reusable
import petlink.android.core_di.calendar.component.CalendarScope
import petlink.android.feature_calendar_data.local_source.CalendarLocalSource
import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_data_impl.local_source.CalendarLocalSourceImpl
import petlink.android.feature_calendar_data_impl.repository.CalendarRepositoryImpl

@Module
interface CalendarDataModule {

    @CalendarScope
    @Binds
    fun bindCalendarRepository(
       calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository

    @CalendarScope
    @Binds
    fun bindCalendarLocalSource(
        calendarLocalSourceImpl: CalendarLocalSourceImpl
    ): CalendarLocalSource


}