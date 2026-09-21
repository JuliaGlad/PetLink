package petlink.android.feature_community_friend_details.fragment.mvi

import petlink.android.core_mvi.MviIntent

sealed interface FriendDetailsIntent : MviIntent {

    class LoadUser(val userId: String) : FriendDetailsIntent

    class AddFriend(val userId: String) : FriendDetailsIntent

    class RemoveFriend(val userId: String) : FriendDetailsIntent

    class TogglePostLike(val userId: String, val postId: String) : FriendDetailsIntent

    class MarkPostViewed(val userId: String, val postId: String) : FriendDetailsIntent

    class CommentAdded(val postId: String) : FriendDetailsIntent
}
