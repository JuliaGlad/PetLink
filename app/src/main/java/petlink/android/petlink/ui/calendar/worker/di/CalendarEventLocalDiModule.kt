package petlink.android.petlink.ui.calendar.worker.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.worker.CalendarEventLocalDi

@Module
class CalendarEventLocalDiModule {

    @EventWorkerScope
    @Provides
    fun provideCalendarEventLocalDi(
        calendarRepository: CalendarRepository
    ): CalendarEventLocalDi = CalendarEventLocalDi(calendarRepository)

}