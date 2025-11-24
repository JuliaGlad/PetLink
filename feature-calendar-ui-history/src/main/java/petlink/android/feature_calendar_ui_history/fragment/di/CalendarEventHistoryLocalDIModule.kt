package petlink.android.feature_calendar_ui_history.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_calendar_domain.usecase.GetHistoryEventsUseCase
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryLocalDi

@Module
class CalendarEventHistoryLocalDIModule {

    @CalendarEventHistoryScope
    @Provides
    fun provideCalendarEventHistoryLocalDI(
        getCalendarHistoryEventsUseCase: GetHistoryEventsUseCase
    ): CalendarEventHistoryLocalDi = CalendarEventHistoryLocalDi(getCalendarHistoryEventsUseCase)

}