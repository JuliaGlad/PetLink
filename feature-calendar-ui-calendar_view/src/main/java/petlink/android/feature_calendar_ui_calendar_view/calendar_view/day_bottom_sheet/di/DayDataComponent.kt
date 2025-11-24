package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.DayDataBottomSheetFragment
import javax.inject.Scope

@DayDataScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        DayDataLocalDIModule::class,
        CalendarDomainModule::class,
        CalendarDataModule::class,
        CalendarDatabaseModule::class
    ]
)
interface DayDataComponent {

    fun inject(dayDataBottomSheetFragment: DayDataBottomSheetFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): DayDataComponent
    }
}

@Scope
annotation class DayDataScope