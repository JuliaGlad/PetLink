package petlink.android.feature_profile_ui_auth.mvi

import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignInUseCase
import javax.inject.Inject

class AuthLocalDI @Inject constructor(
    signInUseCase: SignInUseCase,
    checkIsAuthenticatedUseCase: CheckIsAuthenticatedUseCase
) {

    val actor: AuthActor = AuthActor(signInUseCase, checkIsAuthenticatedUseCase)

    val reducer: AuthReducer = AuthReducer()
}