package petlink.android.feature_profile_ui_create_account.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_create_account.fragment.CreateAccountFragment
import javax.inject.Scope

@CreateAccountScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CreateAccountLocalDIModule::class,
        UserAuthDomainModule::class,
        UserAccountDomainModule::class,
        ProfileDataModule::class,
        ProfileDatabaseModule::class
    ]
)
interface CreateAccountComponent {

    fun inject(fragment: CreateAccountFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): CreateAccountComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateAccountScope