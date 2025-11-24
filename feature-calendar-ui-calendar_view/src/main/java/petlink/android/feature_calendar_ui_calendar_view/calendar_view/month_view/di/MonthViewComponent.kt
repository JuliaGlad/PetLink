package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.MonthViewFragment
import javax.inject.Scope

@MonthViewScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MonthViewLocalDiModule::class,
        CalendarDomainModule::class,
        CalendarDataModule::class,
        CalendarDatabaseModule::class
    ]
)
interface MonthViewComponent {

    fun inject(fragment: MonthViewFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MonthViewComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MonthViewScope