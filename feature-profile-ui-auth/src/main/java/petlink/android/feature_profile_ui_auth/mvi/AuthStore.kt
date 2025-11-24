package petlink.android.feature_profile_ui_auth.mvi

import petlink.android.core_mvi.MviStore

class AuthStore(
    reducer: AuthReducer,
    actor: AuthActor
) : MviStore<AuthPartialState, AuthIntent, AuthMviState, AuthEffect>(reducer, actor) {
    override fun initialStateCreator(): AuthMviState = AuthMviState(AuthState.InitialState)
}