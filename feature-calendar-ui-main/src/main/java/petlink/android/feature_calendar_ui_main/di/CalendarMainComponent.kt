package petlink.android.feature_calendar_ui_main.di

import dagger.Component
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.feature_calendar_ui_main.CalendarMainFragment
import javax.inject.Scope

@CalendarMainScope
@Component(
    dependencies = [CalendarComponent::class],
    modules = [CalendarMainLocalDIModule::class]
)
interface CalendarMainComponent {

    fun inject(fragment: CalendarMainFragment)

    @Component.Factory
    interface Factory{
        fun create(calendarComponent: CalendarComponent): CalendarMainComponent
    }
}

@Scope
annotation class CalendarMainScope