package petlink.android.petlink.domain.usecase.user_auth

import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val authRepository: UserAuthRepository
) {
    suspend fun invoke(password: String){
        authRepository.deleteAccount(password)
    }
}