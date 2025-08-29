package petlink.android.petlink.ui.profile.dialog.update_password.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.dialog.update_password.UpdatePasswordDialogFragment
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

    fun inject(dialogFragment: UpdatePasswordDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ForgotPasswordComponent
    }

}

@Scope
annotation class ForgotPasswordScope