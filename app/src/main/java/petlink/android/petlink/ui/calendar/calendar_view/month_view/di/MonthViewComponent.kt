package petlink.android.petlink.ui.calendar.calendar_view.month_view.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.calendar_view.month_view.MonthViewFragment
import javax.inject.Scope

@MonthViewScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MonthViewLocalDiModule::class,
        MonthViewModule::class
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