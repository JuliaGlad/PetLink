package petlink.android.petlink.ui.profile.auth.mvi

import petlink.android.core_mvi.MviIntent

sealed interface AuthIntent: MviIntent {

    class SignIn(val email: String, val password: String): AuthIntent

    data object Loading: AuthIntent

}