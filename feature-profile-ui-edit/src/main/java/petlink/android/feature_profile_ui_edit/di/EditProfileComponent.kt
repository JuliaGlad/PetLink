package petlink.android.feature_profile_ui_edit.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_edit.EditFragment
import javax.inject.Scope

@EditProfileScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [EditProfileLocalDiModule::class]
)
interface EditProfileComponent {

    fun inject(fragment: EditFragment)

    @Component.Factory
    interface Factory {
        fun create(profileComponent: ProfileComponent): EditProfileComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EditProfileScope