package petlink.android.core_di.profile.modules

import dagger.Binds
import dagger.Module
import dagger.Reusable
import petlink.android.core_di.profile.component.ProfileScope
import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.CreateUserUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.DeleteAccountUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignInUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignOutUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.UpdateEmailUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.UpdatePasswordUseCase
import petlink.android.feature_profile_domain_impl.usecase.user_auth.CheckIsAuthenticatedUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.CreateUserUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.DeleteAccountUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.SignInUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.SignOutUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.UpdateEmailUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_auth.UpdatePasswordUseCaseImpl

@Module
interface UserAuthDomainModule {

    @ProfileScope
    @Binds
    fun bindCheckIsAuthenticatedUseCase(isAuthenticatedUseCaseImpl: CheckIsAuthenticatedUseCaseImpl): CheckIsAuthenticatedUseCase

    @ProfileScope
    @Binds
    fun bindCreateUserUseCase(createUserUseCaseImpl: CreateUserUseCaseImpl): CreateUserUseCase

    @ProfileScope
    @Binds
    fun bindDeleteAccountUseCase(deleteAccountUseCaseImpl: DeleteAccountUseCaseImpl): DeleteAccountUseCase

    @ProfileScope
    @Binds
    fun bindSignOutUseCase(signOutUseCaseImpl: SignOutUseCaseImpl): SignOutUseCase

    @ProfileScope
    @Binds
    fun bindSignInUseCase(signInUseCaseImpl: SignInUseCaseImpl): SignInUseCase

    @ProfileScope
    @Binds
    fun bindUpdateEmailUseCase(updateEmailUseCaseImpl: UpdateEmailUseCaseImpl): UpdateEmailUseCase

    @ProfileScope
    @Binds
    fun bindUpdatePasswordUseCase(updatePasswordUseCaseImpl: UpdatePasswordUseCaseImpl): UpdatePasswordUseCase

}