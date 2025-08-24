package petlink.android.petlink.domain.usecase.user_account

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.domain.mapper.user.user_account.toDomain
import petlink.android.petlink.domain.model.user.user_account.UserDomain
import javax.inject.Inject

class GetUserFullDataUseCase @Inject constructor(
    private val repository: UserAccountRepository
) {
    suspend fun invoke(): UserDomain = repository.getUserData()!!.toDomain()
}