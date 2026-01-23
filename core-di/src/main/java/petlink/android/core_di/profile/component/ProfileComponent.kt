package petlink.android.core_di.profile.component

import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Reusable
import petlink.android.core_di.app.AppComponent
import petlink.android.core_di.profile.modules.ProfileDataModule
import petlink.android.core_di.profile.modules.ProfileDatabaseModule
import petlink.android.core_di.profile.modules.UserAccountDomainModule
import petlink.android.core_di.profile.modules.UserAuthDomainModule
import petlink.android.feature_profile_domain.usecase.user_account.AddUserDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditPetDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.CreateUserUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.DeleteAccountUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignInUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignOutUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.UpdateEmailUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.UpdatePasswordUseCase
import javax.inject.Scope

@ProfileScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        UserAccountDomainModule::class,
        UserAuthDomainModule::class,
        ProfileDataModule::class,
        ProfileDatabaseModule::class
    ]
)
interface ProfileComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ProfileComponent
    }

    fun router(): Router

    fun addUserDataUseCase(): AddUserDataUseCase

    fun editOwnerDataUseCase(): EditOwnerDataUseCase

    fun editPetDataUseCase(): EditPetDataUseCase

    fun getUserFullDataUseCase(): GetUserFullDataUseCase

    fun getUserMainDataUseCase(): GetUserMainDataDomainUseCase

    fun updateBackgroundUseCase(): UpdateBackgroundUseCase

    fun checkIsAuthenticatedUseCase(): CheckIsAuthenticatedUseCase

    fun createUserUseCase(): CreateUserUseCase

    fun deleteAccountUseCase(): DeleteAccountUseCase

    fun signOutUseCase(): SignOutUseCase

    fun signInUseCase(): SignInUseCase

    fun updateEmailUseCase(): UpdateEmailUseCase

    fun updatePasswordUseCase(): UpdatePasswordUseCase

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileScope