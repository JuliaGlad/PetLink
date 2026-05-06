package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CreateNewsCommunityEffect: MviEffect {

    data object NavigateToPrevScreen: CreateNewsCommunityEffect

    data object NavigateToNextScreen: CreateNewsCommunityEffect

    class LaunchImagePicker(val tag: String): CreateNewsCommunityEffect
}