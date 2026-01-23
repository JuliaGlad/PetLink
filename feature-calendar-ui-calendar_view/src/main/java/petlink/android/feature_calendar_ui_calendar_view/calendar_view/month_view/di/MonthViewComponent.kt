package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.di

import dagger.Component
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.MonthViewFragment
import javax.inject.Scope

@MonthViewScope
@Component(
    dependencies = [CalendarComponent::class],
    modules = [MonthViewLocalDiModule::class]
)
interface MonthViewComponent {

    fun inject(fragment: MonthViewFragment)

    @Component.Factory
    interface Factory {
        fun create(calendarComponent: CalendarComponent): MonthViewComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MonthViewScope