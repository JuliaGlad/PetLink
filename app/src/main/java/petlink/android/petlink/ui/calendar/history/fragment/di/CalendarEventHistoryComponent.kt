package petlink.android.petlink.ui.calendar.history.fragment.di

import dagger.Component
import dagger.Module
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.history.fragment.CalendarEventHistoryFragment
import javax.inject.Scope

@CalendarEventHistoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarEventHistoryLocalDIModule::class,
        CalendarEventHistoryModule::class
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