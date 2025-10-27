package petlink.android.petlink.ui.calendar.edit_event.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.calendar.edit_event.EditEventFragment
import javax.inject.Scope

@EditEventScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        EditEventModule::class,
        EditEventLocalDiModule::class
    ]
)
interface EditEventComponent {

    fun inject(fragment: EditEventFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): EditEventComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EditEventScope