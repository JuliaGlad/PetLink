package petlink.android.feature_profile_ui_auth.mvi

import petlink.android.core_mvi.MviState

data class AuthMviState(
    val state: AuthState,
    var emailError: String = "",
    var passwordError: String = ""
): MviState

sealed interface AuthState {

    data object Loading: AuthState

    data object Authenticated: AuthState

    data object InitialState: AuthState

    class Error(val error: AuthError): AuthState

}