package petlink.android.petlink.ui.calendar.main.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainLocalDI

@Module
class CalendarMainLocalDIModule {

    @CalendarMainScope
    @Provides
    fun provideCalendarMainLocalDI(
        repository: CalendarRepository
    ): CalendarMainLocalDI = CalendarMainLocalDI(repository)

}