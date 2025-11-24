package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.mapper.toDomainMainData
import petlink.android.feature_profile_domain.model.user_account.UserMainDataDomain
import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import javax.inject.Inject

class GetUserMainDataDomainUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
): GetUserMainDataDomainUseCase {
    override suspend fun invoke(): UserMainDataDomain = repository.getUserData()!!.toDomainMainData()
}