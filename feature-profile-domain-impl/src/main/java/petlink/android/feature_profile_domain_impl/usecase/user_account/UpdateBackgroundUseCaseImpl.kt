package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import javax.inject.Inject

class UpdateBackgroundUseCaseImpl @Inject constructor(
    private val accountRepository: UserAccountRepository
): UpdateBackgroundUseCase {
    override suspend fun invoke(uri: String){
        accountRepository.updateBackground(uri)
    }
}