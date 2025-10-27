package petlink.android.petlink.ui.calendar.calendar_view.month_view.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi.MonthViewLocalDI

@Module
class MonthViewLocalDiModule {

    @MonthViewScope
    @Provides
    fun provideMonthViewLocalDI(
        calendarRepository: CalendarRepository
    )= MonthViewLocalDI(calendarRepository)

}