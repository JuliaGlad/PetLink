package petlink.android.feature_profile_ui_main.auth_fragment.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface AuthPartialState: MviPartialState {

    data object SignedIn: AuthPartialState

    data object Loading: AuthPartialState

    data object Authenticated: AuthPartialState

    class Error(val exception: AuthError): AuthPartialState

}