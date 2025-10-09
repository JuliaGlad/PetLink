package petlink.android.petlink.ui.calendar.worker.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.data.repository.calendar.CalendarRepositoryImpl
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSource
import petlink.android.petlink.data.source.local.calendar.CalendarLocalSourceImpl

@Module
interface CalendarEventModule {

    @EventWorkerScope
    @Binds
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @EventWorkerScope
    @Binds
    fun bindCalendarLocalSource(calendarLocalSourceImpl: CalendarLocalSourceImpl): CalendarLocalSource

}