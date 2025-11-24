package petlink.android.feature_calendar_ui_history.fragment.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_history.fragment.CalendarEventHistoryFragment
import javax.inject.Scope

@CalendarEventHistoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarEventHistoryLocalDIModule::class,
        CalendarDomainModule::class,
        CalendarDataModule::class,
        CalendarDatabaseModule::class
    ]
)
interface CalendarEventHistoryComponent {

    fun inject(fragment: CalendarEventHistoryFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CalendarEventHistoryComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CalendarEventHistoryScope