package petlink.android.petlink.ui.calendar.history.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.worker.CalendarEventLocalDi

@Module
class CalendarEventHistoryLocalDIModule {

    @CalendarEventHistoryScope
    @Provides
    fun provideCalendarEventHistoryLocalDI(
        calendarRepository: CalendarRepository
    ): CalendarEventLocalDi = CalendarEventLocalDi(calendarRepository)

}