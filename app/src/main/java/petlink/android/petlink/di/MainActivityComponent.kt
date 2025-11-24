package petlink.android.petlink.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.petlink.activity.MainActivity
import javax.inject.Scope

@MainActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MainViewModelModule::class,
        ProfileDataModule::class,
        UserAuthDomainModule::class,
        ProfileDatabaseModule::class
    ]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): MainActivityComponent
    }

}

@Scope
annotation class MainActivityScope