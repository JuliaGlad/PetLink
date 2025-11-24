package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.CreateUserUseCase
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(
    private val userAuthRepository: UserAuthRepository
): CreateUserUseCase {
    override suspend fun invoke(email: String, password: String){
        userAuthRepository.createUserWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}