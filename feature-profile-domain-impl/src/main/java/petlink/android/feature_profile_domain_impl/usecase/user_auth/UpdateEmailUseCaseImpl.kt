package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.UpdateEmailUseCase
import javax.inject.Inject

class UpdateEmailUseCaseImpl @Inject constructor(
    private val repository: UserAuthRepository
): UpdateEmailUseCase{
    override suspend fun invoke(password: String, newEmail: String){
        repository.updateEmail(password, newEmail)
    }
}