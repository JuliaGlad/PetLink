package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.core_mvi.MviEffect

sealed interface ProfileEffect: MviEffect {

    data object NavigateToEdit: ProfileEffect

    data object NavigateToFriends: ProfileEffect

    data object NavigateToMyData: ProfileEffect

    data object NavigateToAchievements: ProfileEffect

    data object NavigateToSettings: ProfileEffect

    data object LaunchImagePicker: ProfileEffect

    data object ShowPosts: ProfileEffect

}