package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface CreateAccountPartialState: MviPartialState {

    data object Loading: CreateAccountPartialState

    data object UserAuthenticated: CreateAccountPartialState

    class Error(val throwable: Throwable): CreateAccountPartialState

}