package petlink.android.feature_calendar_ui_add_event.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_calendar_domain.usecase.AddCalendarEventUseCase
import petlink.android.feature_calendar_ui_add_event.mvi.AddEventLocalDI

@Module
class AddEventLocalDiModule {

    @AddEventScope
    @Provides
    fun provideAddEventLocalDi(
        addCalendarEventUseCase: AddCalendarEventUseCase
    ): AddEventLocalDI = AddEventLocalDI(addCalendarEventUseCase)

}