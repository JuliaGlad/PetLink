package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.DayDataBottomSheetFragment
import javax.inject.Scope

@DayDataScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        DayDataLocalDIModule::class,
        DayDataModule::class
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