package petlink.android.petlink.ui.calendar.main.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment
import javax.inject.Scope

@CalendarMainScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarMainLocalDIModule::class,
        CalendarMainModule::class
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