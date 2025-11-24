package petlink.android.feature_calendar_ui_edit_event.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_calendar_domain.usecase.DeleteCalendarEventUseCase
import petlink.android.feature_calendar_domain.usecase.UpdateCalendarEventUseCase
import petlink.android.feature_calendar_ui_edit_event.fragment.mvi.EditEventLocalDI

@Module
class EditEventLocalDiModule {

    @EditEventScope
    @Provides
    fun provideEditEventLocalDI(
        deleteCalendarEventUseCase: DeleteCalendarEventUseCase,
        updateCalendarEventUseCase: UpdateCalendarEventUseCase
    ): EditEventLocalDI = EditEventLocalDI(deleteCalendarEventUseCase, updateCalendarEventUseCase)

}