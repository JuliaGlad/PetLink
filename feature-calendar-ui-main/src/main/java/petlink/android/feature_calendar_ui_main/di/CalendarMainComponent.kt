package petlink.android.feature_calendar_ui_main.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_main.CalendarMainFragment
import javax.inject.Scope

@CalendarMainScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarMainLocalDIModule::class,
        CalendarDataModule::class,
        CalendarDomainModule::class,
        CalendarDatabaseModule::class
    ]
)
interface CalendarMainComponent {

    fun inject(fragment: CalendarMainFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): CalendarMainComponent
    }
}

@Scope
annotation class CalendarMainScope