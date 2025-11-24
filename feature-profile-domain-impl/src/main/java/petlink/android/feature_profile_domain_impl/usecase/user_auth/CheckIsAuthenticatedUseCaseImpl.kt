package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import javax.inject.Inject

class CheckIsAuthenticatedUseCaseImpl @Inject constructor(
    private val repository: UserAuthRepository
): CheckIsAuthenticatedUseCase {
    override fun invoke(): Boolean = repository.isAuthenticated()
}