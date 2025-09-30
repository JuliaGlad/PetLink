package petlink.android.petlink.ui.calendar.add_event.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.add_event.AddEventFragment
import javax.inject.Scope

@AddEventScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        AddEventModule::class,
        AddEventLocalDiModule::class
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