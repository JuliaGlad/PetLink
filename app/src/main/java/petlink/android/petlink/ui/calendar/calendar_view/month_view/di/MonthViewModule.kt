package petlink.android.petlink.ui.calendar.calendar_view.month_view.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.data.repository.calendar.CalendarRepositoryImpl
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSource
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSourceImpl

@Module
interface MonthViewModule {

    @MonthViewScope
    @Binds
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @MonthViewScope
    @Binds
    fun bindCalendarLocalSource(calendarLocalSourceImpl: CalendarLocalSourceImpl): CalendarLocalSource

}