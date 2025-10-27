package petlink.android.petlink.ui.calendar.add_event.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventLocalDI

@Module
class AddEventLocalDiModule {

    @AddEventScope
    @Provides
    fun provideAddEventLocalDi(
        calendarRepository: CalendarRepository
    ): AddEventLocalDI = AddEventLocalDI(
        calendarRepository = calendarRepository
    )

}