package petlink.android.petlink.ui.profile.settings.dialog.logout.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.settings.dialog.delete_account.DeleteAccountDialogFragment
import petlink.android.petlink.ui.profile.settings.dialog.logout.LogoutDialogFragment
import javax.inject.Scope

@LogoutScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        LogoutModule::class,
        LogoutViewModelModule::class
    ]
)
interface LogoutComponent {

    fun inject(dialogFragment: LogoutDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): LogoutComponent
    }

}

@Scope
annotation class LogoutScope