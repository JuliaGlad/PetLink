package petlink.android.petlink.ui.calendar.edit_event.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.data.repository.calendar.CalendarRepositoryImpl
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSource
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSourceImpl

@Module
interface EditEventModule {

    @EditEventScope
    @Binds
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @EditEventScope
    @Binds
    fun bindCalendarLocalSource(calendarLocalSourceImpl: CalendarLocalSourceImpl): CalendarLocalSource
}