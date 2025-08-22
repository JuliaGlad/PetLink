package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import petlink.android.core_mvi.MviStore

class CreateAccountStore(
    actor: CreateAccountActor,
    reducer: CreateAccountReducer
) : MviStore<
        CreateAccountPartialState,
        CreateAccountIntent,
        CreateAccountMviState,
        CreateAccountEffect
        >(actor = actor, reducer = reducer) {
    override fun initialStateCreator(): CreateAccountMviState = CreateAccountMviState(CreateAccountState.Init)
}