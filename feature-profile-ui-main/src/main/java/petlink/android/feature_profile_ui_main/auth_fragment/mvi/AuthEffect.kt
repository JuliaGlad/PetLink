package petlink.android.feature_profile_ui_main.auth_fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface AuthEffect: MviEffect {

    data object CreateAccount: AuthEffect

    data object ForgotPassword: AuthEffect

    data object NavigateToProfile: AuthEffect

    class SetPasswordError(val value: String): AuthEffect

    class SetEmailError(val value: String): AuthEffect

}