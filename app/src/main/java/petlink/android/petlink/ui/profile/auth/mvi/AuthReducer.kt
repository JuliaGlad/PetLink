package petlink.android.petlink.ui.profile.auth.mvi

import petlink.android.core_mvi.MviReducer

class AuthReducer: MviReducer<AuthPartialState, AuthMviState> {
    override fun reduce(
        prevState: AuthMviState,
        partialState: AuthPartialState
    ): AuthMviState =
        when(partialState){
            is AuthPartialState.Error -> updateError(prevState, partialState.exception)
            AuthPartialState.SignedIn -> updateSignedIn(prevState)
            AuthPartialState.Loading -> updateLoading(prevState)
            AuthPartialState.Authenticated -> updateAuthenticated(prevState)
        }

    private fun updateAuthenticated(prevState: AuthMviState) =
        prevState.copy(state = AuthState.Authenticated)

    private fun updateLoading(prevState: AuthMviState) =
        prevState.copy(state = AuthState.Loading)

    private fun updateSignedIn(prevState: AuthMviState) =
        prevState.copy(state = AuthState.Authenticated)

    private fun updateError(prevState: AuthMviState, error: AuthError) =
        prevState.copy(state = AuthState.Error(error))

}