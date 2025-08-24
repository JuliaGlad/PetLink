package petlink.android.petlink.ui.profile.edit.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.edit.EditFragment
import javax.inject.Scope

@EditProfileScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        EditProfileLocalDiModule::class,
        EditProfileModule::class
    ]
)
interface EditProfileComponent {

    fun inject(fragment: EditFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): EditProfileComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EditProfileScope