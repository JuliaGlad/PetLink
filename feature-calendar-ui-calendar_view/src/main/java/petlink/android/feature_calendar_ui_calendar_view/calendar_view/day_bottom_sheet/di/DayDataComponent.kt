package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.di

import dagger.Component
import petlink.android.core_di.app.AppComponent
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.DayDataBottomSheetFragment
import javax.inject.Scope

@DayDataScope
@Component(
    dependencies = [CalendarComponent::class],
    modules = [DayDataLocalDIModule::class]
)
interface DayDataComponent {

    fun inject(dayDataBottomSheetFragment: DayDataBottomSheetFragment)

    @Component.Factory
    interface Factory{
        fun create(calendarComponent: CalendarComponent): DayDataComponent
    }
}

@Scope
annotation class DayDataScope