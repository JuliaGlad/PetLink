package petlink.android.feature_calendar_ui_add_event.worker.di

import dagger.Component
import petlink.android.core_di.calendar.component.CalendarComponent
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_calendar_ui_add_event.worker.CalendarEventWorker
import javax.inject.Scope

@EventWorkerScope
@Component(
    dependencies = [
        ProfileComponent::class,
        CalendarComponent::class
    ]
)
interface CalendarEventComponent {
    fun inject(worker: CalendarEventWorker)

    @Component.Factory
    interface Factory {
        fun create(
            profileComponent: ProfileComponent,
            calendarComponent: CalendarComponent
        ): CalendarEventComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EventWorkerScope