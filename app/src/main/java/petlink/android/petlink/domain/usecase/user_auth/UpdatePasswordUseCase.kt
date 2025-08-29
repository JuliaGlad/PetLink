package petlink.android.petlink.domain.usecase.user_auth

import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(){
        repository.updatePassword()
    }
}