package petlink.android.feature_profile_ui_create_account.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CreateAccountEffect: MviEffect {
    data object NavigateToNextScreen: CreateAccountEffect
    data object NavigateBack: CreateAccountEffect
    data object ShowDataDialog: CreateAccountEffect
    data object LaunchImagePicker: CreateAccountEffect
}