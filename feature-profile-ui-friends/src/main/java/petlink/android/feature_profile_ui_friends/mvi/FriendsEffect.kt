package petlink.android.feature_profile_ui_friends.mvi

import petlink.android.core_mvi.MviEffect

sealed interface FriendsEffect : MviEffect {
    data object NavigateBack : FriendsEffect
    class OpenUser(val userId: String) : FriendsEffect
    class ShowDeleteDialog(val userId: String, val userName: String) : FriendsEffect
}
