package petlink.android.feature_profile_ui_edit.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_domain_impl.di.UserAuthDomainModule
import petlink.android.feature_profile_ui_edit.EditFragment
import javax.inject.Scope

@EditProfileScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        EditProfileLocalDiModule::class,
        UserAccountDomainModule::class,
        UserAuthDomainModule::class,
        ProfileDataModule::class,
        ProfileDatabaseModule::class
    ]
)
interface EditProfileComponent {

    fun inject(fragment: EditFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): EditProfileComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EditProfileScope