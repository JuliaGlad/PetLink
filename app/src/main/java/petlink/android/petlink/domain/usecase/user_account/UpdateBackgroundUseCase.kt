package petlink.android.petlink.domain.usecase.user_account

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import javax.inject.Inject

class UpdateBackgroundUseCase @Inject constructor(
    private val accountRepository: UserAccountRepository
) {
    suspend fun invoke(uri: String){
        accountRepository.updateBackground(uri)
    }
}