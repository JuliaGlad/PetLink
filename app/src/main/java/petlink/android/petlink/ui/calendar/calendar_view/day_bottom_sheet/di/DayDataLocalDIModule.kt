package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataLocalDI

@Module
class DayDataLocalDIModule {

    @DayDataScope
    @Provides
    fun provideDayDataLocalDI(
        repository: CalendarRepository
    ): DayDataLocalDI = DayDataLocalDI(repository)

}