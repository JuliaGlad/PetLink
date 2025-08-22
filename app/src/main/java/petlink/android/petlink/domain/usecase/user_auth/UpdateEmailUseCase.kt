package petlink.android.petlink.domain.usecase.user_auth

import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    private val repository: UserAuthRepository
){
    suspend fun invoke(password: String, newEmail: String){
        repository.updateEmail(password, newEmail)
    }
}