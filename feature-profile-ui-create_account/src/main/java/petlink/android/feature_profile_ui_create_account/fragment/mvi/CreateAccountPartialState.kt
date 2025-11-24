package petlink.android.feature_profile_ui_create_account.fragment.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface CreateAccountPartialState: MviPartialState {

    data object Loading: CreateAccountPartialState

    data object UserAuthenticated: CreateAccountPartialState

    class Error(val throwable: Throwable): CreateAccountPartialState

}