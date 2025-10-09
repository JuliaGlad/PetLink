package petlink.android.petlink.ui.calendar.worker.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.worker.CalendarEventWorker
import javax.inject.Scope

@EventWorkerScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarEventModule::class,
        CalendarEventLocalDiModule::class
    ]
)
interface CalendarEventComponent {
    fun inject(worker: CalendarEventWorker)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CalendarEventComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EventWorkerScope