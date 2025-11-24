package petlink.android.feature_calendar_ui_main.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_calendar_domain.usecase.GetCalendarEventsUseCase
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainLocalDI

@Module
class CalendarMainLocalDIModule {

    @CalendarMainScope
    @Provides
    fun provideCalendarMainLocalDI(
        getCalendarEventsUseCase: GetCalendarEventsUseCase
    ): CalendarMainLocalDI = CalendarMainLocalDI(getCalendarEventsUseCase)

}