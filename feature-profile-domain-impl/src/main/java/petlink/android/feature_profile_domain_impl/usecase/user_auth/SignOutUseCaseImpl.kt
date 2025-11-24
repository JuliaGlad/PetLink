package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.SignOutUseCase
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
): SignOutUseCase {
    override suspend fun invoke(){ authRepository.signOut() }
}