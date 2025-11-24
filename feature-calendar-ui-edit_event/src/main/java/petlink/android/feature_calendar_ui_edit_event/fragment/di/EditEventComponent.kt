package petlink.android.feature_calendar_ui_edit_event.fragment.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_calendar_data_impl.di.CalendarDataModule
import petlink.android.feature_calendar_data_impl.local_db.db.CalendarDatabaseModule
import petlink.android.feature_calendar_domain_impl.di.CalendarDomainModule
import petlink.android.feature_calendar_ui_edit_event.fragment.EditEventFragment
import javax.inject.Scope

@EditEventScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        EditEventLocalDiModule::class,
        CalendarDomainModule::class,
        CalendarDataModule::class,
        CalendarDatabaseModule::class
    ]
)
interface EditEventComponent {

    fun inject(fragment: EditEventFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): EditEventComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EditEventScope