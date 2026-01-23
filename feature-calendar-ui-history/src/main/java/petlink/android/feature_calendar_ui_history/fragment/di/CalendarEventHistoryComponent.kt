package petlink.android.feature_calendar_ui_history.fragment.di

import dagger.Component
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.feature_calendar_ui_history.fragment.CalendarEventHistoryFragment
import javax.inject.Scope

@CalendarEventHistoryScope
@Component(
    dependencies = [CalendarComponent::class],
    modules = [CalendarEventHistoryLocalDIModule::class]
)
interface CalendarEventHistoryComponent {

    fun inject(fragment: CalendarEventHistoryFragment)

    @Component.Factory
    interface Factory {
        fun create(calendarComponent: CalendarComponent): CalendarEventHistoryComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CalendarEventHistoryScope