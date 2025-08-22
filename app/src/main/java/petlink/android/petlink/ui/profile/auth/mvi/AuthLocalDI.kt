package petlink.android.petlink.ui.profile.auth.mvi

import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.domain.usecase.user_auth.SignInUseCase
import petlink.android.petlink.domain.usecase.user_auth.UpdatePasswordUseCase
import javax.inject.Inject

class AuthLocalDI @Inject constructor(
    authRepository: UserAuthRepository
) {
    private val signInUseCase = SignInUseCase(authRepository)

    private val updatePasswordUseCase = UpdatePasswordUseCase(authRepository)

    val actor: AuthActor = AuthActor(signInUseCase, updatePasswordUseCase)

    val reducer: AuthReducer = AuthReducer()
}