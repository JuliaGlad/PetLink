package petlink.android.feature_profile_ui_main.auth_fragment.mvi

import petlink.android.core_mvi.MviIntent

sealed interface AuthIntent: MviIntent {

    class SignIn(val email: String, val password: String): AuthIntent

    data object CheckAuth: AuthIntent

    data object Loading: AuthIntent

}