package petlink.android.petlink.ui.profile.auth.mvi

import petlink.android.core_mvi.MviStore

class AuthStore(
    reducer: AuthReducer,
    actor: AuthActor
) : MviStore<AuthPartialState, AuthIntent, AuthMviState, AuthEffect>(reducer, actor) {
    override fun initialStateCreator(): AuthMviState = AuthMviState(AuthState.InitialState)
}