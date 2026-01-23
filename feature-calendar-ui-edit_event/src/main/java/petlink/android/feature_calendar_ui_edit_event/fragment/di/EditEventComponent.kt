package petlink.android.feature_calendar_ui_edit_event.fragment.di

import dagger.Component
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.feature_calendar_ui_edit_event.fragment.EditEventFragment
import javax.inject.Scope

@EditEventScope
@Component(
    dependencies = [CalendarComponent::class],
    modules = [EditEventLocalDiModule::class]
)
interface EditEventComponent {

    fun inject(fragment: EditEventFragment)

    @Component.Factory
    interface Factory {
        fun create(calendarComponent: CalendarComponent): EditEventComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EditEventScope