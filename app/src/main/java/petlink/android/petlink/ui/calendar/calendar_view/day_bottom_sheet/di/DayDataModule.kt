package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.data.repository.calendar.CalendarRepositoryImpl
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSource
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSourceImpl

@Module
interface DayDataModule {

    @DayDataScope
    @Binds
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @DayDataScope
    @Binds
    fun bindCalendarLocalSource(calendarLocalSourceImpl: CalendarLocalSourceImpl): CalendarLocalSource
}