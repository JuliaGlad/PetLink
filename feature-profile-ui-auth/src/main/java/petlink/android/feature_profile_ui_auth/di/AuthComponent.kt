package petlink.android.feature_profile_ui_auth.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_auth.AuthFragment
import javax.inject.Scope

@AuthScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        AuthLocalDIModule::class,
        UserAuthDomainModule::class,
        ProfileDataModule::class,
        ProfileDatabaseModule::class
    ]
)
interface AuthComponent {

    fun inject(fragment: AuthFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): AuthComponent
    }

}

@Scope
annotation class AuthScope