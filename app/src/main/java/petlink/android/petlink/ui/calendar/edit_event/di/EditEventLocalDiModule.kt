package petlink.android.petlink.ui.calendar.edit_event.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventLocalDI

@Module
class EditEventLocalDiModule {

    @EditEventScope
    @Provides
    fun provideEditEventLocalDI(
        repository: CalendarRepository
    ): EditEventLocalDI = EditEventLocalDI(repository)

}