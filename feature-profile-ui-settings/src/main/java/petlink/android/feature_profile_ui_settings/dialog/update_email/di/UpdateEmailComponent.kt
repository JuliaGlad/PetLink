package petlink.android.feature_profile_ui_settings.dialog.update_email.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_settings.dialog.update_email.UpdateEmailDialogFragment
import javax.inject.Scope

@UpdateEmailScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        UpdateEmailViewModelModule::class,
        ProfileDataModule::class,
        UserAuthDomainModule::class,
        UserAccountDomainModule::class,
        ProfileDatabaseModule::class
    ]
)
interface UpdateEmailComponent {

    fun inject(dialogFragment: UpdateEmailDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): UpdateEmailComponent
    }

}

@Scope
annotation class UpdateEmailScope