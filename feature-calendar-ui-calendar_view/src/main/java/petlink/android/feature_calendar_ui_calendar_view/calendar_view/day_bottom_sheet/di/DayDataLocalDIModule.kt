package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_calendar_domain.usecase.GetEventsByDateUseCase
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.mvi.DayDataLocalDI

@Module
class DayDataLocalDIModule {

    @DayDataScope
    @Provides
    fun provideDayDataLocalDI(
        getEventsByDateUseCase: GetEventsByDateUseCase
    ): DayDataLocalDI = DayDataLocalDI(getEventsByDateUseCase)

}