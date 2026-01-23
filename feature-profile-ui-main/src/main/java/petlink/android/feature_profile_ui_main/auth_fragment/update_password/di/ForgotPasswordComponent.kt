package petlink.android.feature_profile_ui_main.auth_fragment.update_password.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_main.auth_fragment.update_password.UpdatePasswordDialogFragment
import javax.inject.Scope

@ForgotPasswordScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [ForgotPasswordViewModelModule::class]
)
interface ForgotPasswordComponent {

    fun inject(dialogFragment: UpdatePasswordDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(profileComponent: ProfileComponent): ForgotPasswordComponent
    }

}

@Scope
annotation class ForgotPasswordScope