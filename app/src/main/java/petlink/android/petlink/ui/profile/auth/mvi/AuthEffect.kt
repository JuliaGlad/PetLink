package petlink.android.petlink.ui.profile.auth.mvi

import petlink.android.core_mvi.MviEffect

sealed interface AuthEffect: MviEffect {

    data object CreateAccount: AuthEffect

    data object ForgotPassword: AuthEffect

    data object NavigateToProfile: AuthEffect

    class SetPasswordError(val value: String): AuthEffect

    class SetEmailError(val value: String): AuthEffect

}