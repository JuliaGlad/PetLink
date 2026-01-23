package petlink.android.feature_profile_ui_settings.dialog.logout.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_settings.dialog.logout.LogoutDialogFragment
import javax.inject.Scope

@LogoutScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [LogoutViewModelModule::class]
)
interface LogoutComponent {

    fun inject(dialogFragment: LogoutDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(profileComponent: ProfileComponent): LogoutComponent
    }

}

@Scope
annotation class LogoutScope