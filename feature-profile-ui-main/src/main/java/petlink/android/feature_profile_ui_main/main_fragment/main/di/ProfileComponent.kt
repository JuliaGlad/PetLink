package petlink.android.feature_profile_ui_main.main_fragment.main.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_main.main_fragment.main.ProfileFragment
import javax.inject.Scope

@ProfileScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ProfileDataModule::class,
        ProfileDatabaseModule::class,
        UserAccountDomainModule::class,
        UserAuthDomainModule::class,
        ProfileLocalDIModule::class
    ]
)
interface ProfileComponent {

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(appDependencies: AppComponent): ProfileComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileScope