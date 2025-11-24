package petlink.android.feature_calendar_ui_add_event.worker.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_add_event.worker.CalendarEventWorker
import javax.inject.Scope

@EventWorkerScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarDomainModule::class,
        CalendarDataModule::class,
        CalendarDatabaseModule::class
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