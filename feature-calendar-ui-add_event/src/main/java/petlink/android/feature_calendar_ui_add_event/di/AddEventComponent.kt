package petlink.android.feature_calendar_ui_add_event.di

import dagger.Component
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.feature_calendar_ui_add_event.AddEventFragment
import javax.inject.Scope

@AddEventScope
@Component(
    dependencies = [CalendarComponent::class],
    modules = [AddEventLocalDiModule::class]
)
interface AddEventComponent {

    fun inject(fragment: AddEventFragment)

    @Component.Factory
    interface Factory {
        fun create(calendarComponent: CalendarComponent): AddEventComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AddEventScope