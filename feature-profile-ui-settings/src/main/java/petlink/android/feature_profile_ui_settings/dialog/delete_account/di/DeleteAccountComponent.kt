package petlink.android.feature_profile_ui_settings.dialog.delete_account.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_settings.dialog.delete_account.DeleteAccountDialogFragment
import javax.inject.Scope

@DeleteAccountScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        DeleteViewModelModule::class,
        ProfileDataModule::class,
        UserAuthDomainModule::class,
        UserAccountDomainModule::class,
        ProfileDatabaseModule::class
    ]
)
interface DeleteAccountComponent {

    fun inject(dialogFragment: DeleteAccountDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DeleteAccountComponent
    }

}

@Scope
annotation class DeleteAccountScope