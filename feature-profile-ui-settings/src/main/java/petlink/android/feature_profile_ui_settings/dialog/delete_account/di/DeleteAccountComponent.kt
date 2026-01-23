package petlink.android.feature_profile_ui_settings.dialog.delete_account.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_settings.dialog.delete_account.DeleteAccountDialogFragment
import javax.inject.Scope

@DeleteAccountScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [DeleteViewModelModule::class]
)
interface DeleteAccountComponent {

    fun inject(dialogFragment: DeleteAccountDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(profileComponent: ProfileComponent): DeleteAccountComponent
    }

}

@Scope
annotation class DeleteAccountScope