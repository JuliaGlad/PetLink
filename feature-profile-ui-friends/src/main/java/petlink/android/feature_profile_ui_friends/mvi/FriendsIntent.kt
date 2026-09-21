package petlink.android.feature_profile_ui_friends.mvi

import petlink.android.core_mvi.MviIntent

sealed interface FriendsIntent : MviIntent {
    data object LoadFriends : FriendsIntent
    class Search(val query: String) : FriendsIntent
    class AddFriend(val userId: String) : FriendsIntent
    class RemoveFriend(val userId: String) : FriendsIntent
}
