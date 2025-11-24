package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.DeleteAccountUseCase
import javax.inject.Inject

class DeleteAccountUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
): DeleteAccountUseCase {
    override suspend fun invoke(password: String){
        authRepository.deleteAccount(password)
    }
}