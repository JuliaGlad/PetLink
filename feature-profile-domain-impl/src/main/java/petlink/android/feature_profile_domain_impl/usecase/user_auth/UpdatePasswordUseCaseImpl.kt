package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.UpdatePasswordUseCase
import javax.inject.Inject

class UpdatePasswordUseCaseImpl @Inject constructor(
    private val repository: UserAuthRepository
): UpdatePasswordUseCase {
    override suspend fun invoke(){
        repository.updatePassword()
    }
}