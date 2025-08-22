package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CreateAccountEffect: MviEffect {
    data object NavigateToNextScreen: CreateAccountEffect
    data object NavigateBack: CreateAccountEffect
}