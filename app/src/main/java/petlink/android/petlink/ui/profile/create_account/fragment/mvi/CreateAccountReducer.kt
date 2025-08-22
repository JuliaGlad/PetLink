package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import petlink.android.core_mvi.MviReducer

class CreateAccountReducer : MviReducer<
        CreateAccountPartialState,
        CreateAccountMviState> {
    override fun reduce(
        prevState: CreateAccountMviState,
        partialState: CreateAccountPartialState
    ): CreateAccountMviState =
        when (partialState) {
            is CreateAccountPartialState.Error -> updateError(prevState, partialState.throwable)
            CreateAccountPartialState.Loading -> updateLoading(prevState)
            CreateAccountPartialState.UserAuthenticated -> updateUserAuthenticated(prevState)
        }

    private fun updateUserAuthenticated(prevState: CreateAccountMviState) =
        prevState.copy(state = CreateAccountState.AccountCreated)

    private fun updateLoading(prevState: CreateAccountMviState) =
        prevState.copy(state = CreateAccountState.Loading)

    private fun updateError(prevState: CreateAccountMviState, throwable: Throwable)=
            prevState.copy(state = CreateAccountState.Error(throwable))
}