package petlink.android.petlink.domain.usecase.user_auth

import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) {
    suspend fun invoke(email: String, password: String){
        userAuthRepository.createUserWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}