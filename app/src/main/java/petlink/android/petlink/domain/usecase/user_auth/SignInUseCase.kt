package petlink.android.petlink.domain.usecase.user_auth

import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: UserAuthRepository
) {
    suspend fun invoke(email: String, password: String){
        authRepository.signInWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}