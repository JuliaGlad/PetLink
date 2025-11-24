package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_calendar_domain.usecase.GetEventsFromMonthUseCase
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewLocalDI

@Module
class MonthViewLocalDiModule {

    @MonthViewScope
    @Provides
    fun provideMonthViewLocalDI(
        getEventsFromMonthUseCase: GetEventsFromMonthUseCase
    )= MonthViewLocalDI(getEventsFromMonthUseCase)

}