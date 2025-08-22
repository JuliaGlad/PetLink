package petlink.android.petlink.ui.profile.auth.dialog.forgot_password.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.auth.dialog.forgot_password.ForgotPasswordDialogFragment
import javax.inject.Scope

@ForgotPasswordScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ForgotPasswordModule::class,
        ForgotPasswordViewModelModule::class
    ]
)
interface ForgotPasswordComponent {

    fun inject(dialogFragment: ForgotPasswordDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ForgotPasswordComponent
    }

}

@Scope
annotation class ForgotPasswordScope