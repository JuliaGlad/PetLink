package petlink.android.feature_profile_ui_main.auth_fragment.update_password.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_main.auth_fragment.update_password.UpdatePasswordDialogFragment
import javax.inject.Scope

@ForgotPasswordScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ForgotPasswordViewModelModule::class,
        UserAuthDomainModule::class,
        ProfileDataModule::class,
        ProfileDatabaseModule::class
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