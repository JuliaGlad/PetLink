package petlink.android.feature_profile_ui_create_account.fragment.mvi

import petlink.android.core_mvi.MviState

data class CreateAccountMviState(val state: CreateAccountState): MviState

sealed interface CreateAccountState{
    data object Init: CreateAccountState
    data object Loading: CreateAccountState
    data object AccountCreated: CreateAccountState
    class Error(val throwable: Throwable): CreateAccountState
}