package petlink.android.feature_calendar_ui_add_event.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_add_event.AddEventFragment
import javax.inject.Scope

@AddEventScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        AddEventLocalDiModule::class,
        CalendarDomainModule::class,
        CalendarDataModule::class,
        CalendarDatabaseModule::class
    ]
)
interface AddEventComponent {

    fun inject(fragment: AddEventFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): AddEventComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AddEventScope