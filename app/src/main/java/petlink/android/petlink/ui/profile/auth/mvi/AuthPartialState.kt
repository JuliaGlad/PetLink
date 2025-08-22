package petlink.android.petlink.ui.profile.auth.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface AuthPartialState: MviPartialState {

    data object SignedIn: AuthPartialState

    data object Loading: AuthPartialState

    class Error(val exception: AuthError): AuthPartialState

}