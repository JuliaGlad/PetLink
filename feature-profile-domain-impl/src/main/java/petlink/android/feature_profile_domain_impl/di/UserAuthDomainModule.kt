package petlink.android.feature_profile_domain_impl.di

import dagger.Binds
import dagger.Module
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
import petlink.android.feature_profile_domain_impl.usecase.user_account.AddUserDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.EditOwnerDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.EditPetDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserFullDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserMainDataDomainUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.UpdateBackgroundUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.CheckIsAuthenticatedUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.CreateUserUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.DeleteAccountUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.SignInUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.SignOutUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.UpdateEmailUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.UpdatePasswordUseCaseImpl

@Module
interface UserAuthDomainModule {

    @Binds
    fun bindCheckIsAuthenticatedUseCase(isAuthenticatedUseCaseImpl: CheckIsAuthenticatedUseCaseImpl): CheckIsAuthenticatedUseCase

    @Binds
    fun bindCreateUserUseCase(createUserUseCaseImpl: CreateUserUseCaseImpl): CreateUserUseCase

    @Binds
    fun bindDeleteAccountUseCase(deleteAccountUseCaseImpl: DeleteAccountUseCaseImpl): DeleteAccountUseCase

    @Binds
    fun bindSignOutUseCase(signOutUseCaseImpl: SignOutUseCaseImpl): SignOutUseCase

    @Binds
    fun bindSignInUseCase(signInUseCaseImpl: SignInUseCaseImpl): SignInUseCase

    @Binds
    fun bindUpdateEmailUseCase(updateEmailUseCaseImpl: UpdateEmailUseCaseImpl): UpdateEmailUseCase

    @Binds
    fun bindUpdatePasswordUseCase(updatePasswordUseCaseImpl: UpdatePasswordUseCaseImpl): UpdatePasswordUseCase

}