package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserDomain
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import javax.inject.Inject

class GetUserFullDataUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
): GetUserFullDataUseCase {
    override suspend fun invoke(): UserDomain = repository.getUserData()!!
}