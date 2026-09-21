package petlink.android.feature_community_friend_details.fragment.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_community_friend_details.fragment.model.FriendDetailsContent

sealed interface FriendDetailsPartialState : MviPartialState {

    data object Loading : FriendDetailsPartialState

    class DataLoaded(val content: FriendDetailsContent) : FriendDetailsPartialState

    data object FriendAdded : FriendDetailsPartialState

    data object FriendRemoved : FriendDetailsPartialState

    class PostUpdated(
        val postId: String,
        val likesCount: Int? = null,
        val likedByMe: Boolean? = null,
        val viewsCount: Int? = null,
        val commentsCount: Int? = null
    ) : FriendDetailsPartialState

    class Error(val throwable: Throwable) : FriendDetailsPartialState
}
