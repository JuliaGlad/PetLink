package petlink.android.feature_calendar_data_impl.di

import dagger.Binds
import dagger.Module
import petlink.android.feature_calendar_data.local_source.CalendarLocalSource
import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_data_impl.local_source.CalendarLocalSourceImpl
import petlink.android.feature_calendar_data_impl.repository.CalendarRepositoryImpl

@Module
interface CalendarDataModule {

    @Binds
    fun bindCalendarRepository(
       calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository

    @Binds
    fun bindCalendarLocalSource(
        calendarLocalSourceImpl: CalendarLocalSourceImpl
    ): CalendarLocalSource


}