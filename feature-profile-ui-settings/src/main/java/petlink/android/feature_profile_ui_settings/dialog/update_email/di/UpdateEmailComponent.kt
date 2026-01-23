package petlink.android.feature_profile_ui_settings.dialog.update_email.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_settings.dialog.update_email.UpdateEmailDialogFragment
import javax.inject.Scope

@UpdateEmailScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [UpdateEmailViewModelModule::class]
)
interface UpdateEmailComponent {

    fun inject(dialogFragment: UpdateEmailDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(profileComponent: ProfileComponent): UpdateEmailComponent
    }

}

@Scope
annotation class UpdateEmailScope