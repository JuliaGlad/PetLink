package petlink.android.petlink.domain.usecase.user_account

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.domain.mapper.user.user_account.toDomainMainData
import petlink.android.petlink.domain.model.user.user_account.UserMainDataDomain
import javax.inject.Inject

class GetUserMainDataDomainUseCase @Inject constructor(
    private val repository: UserAccountRepository
) {
    suspend fun invoke(): UserMainDataDomain = repository.getUserData()!!.toDomainMainData()
}