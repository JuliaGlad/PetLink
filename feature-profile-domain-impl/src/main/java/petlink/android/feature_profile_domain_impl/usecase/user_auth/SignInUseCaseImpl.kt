package petlink.android.feature_profile_domain_impl.usecase.user_auth

import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_domain.usecase.user_auth.SignInUseCase
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
): SignInUseCase {
    override suspend fun invoke(email: String, password: String){
        authRepository.signInWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}