package petlink.android.feature_profile_ui_settings.dialog.logout.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_settings.dialog.logout.LogoutDialogFragment
import javax.inject.Scope

@LogoutScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        LogoutViewModelModule::class,
        ProfileDataModule::class,
        UserAuthDomainModule::class,
        UserAccountDomainModule::class,
        ProfileDatabaseModule::class
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