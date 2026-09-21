package petlink.android.feature_community_friend_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel
import petlink.android.feature_community_friend_details.fragment.model.FriendDetailsContent

class FriendDetailsReducer : MviReducer<FriendDetailsPartialState, FriendDetailsState> {
    override fun reduce(
        prevState: FriendDetailsState,
        partialState: FriendDetailsPartialState
    ): FriendDetailsState =
        when (partialState) {
            FriendDetailsPartialState.Loading -> prevState.copy(value = LceState.Loading)
            is FriendDetailsPartialState.DataLoaded -> prevState.copy(
                value = LceState.Content(partialState.content)
            )
            FriendDetailsPartialState.FriendAdded -> prevState.withFriendship(isFriend = true)
            FriendDetailsPartialState.FriendRemoved -> prevState.withFriendship(isFriend = false)
            is FriendDetailsPartialState.PostUpdated -> updatePost(prevState, partialState)
            is FriendDetailsPartialState.Error -> prevState.copy(
                value = LceState.Error(partialState.throwable)
            )
        }

    private fun FriendDetailsState.withFriendship(isFriend: Boolean): FriendDetailsState {
        val current = (value as? LceState.Content)?.data ?: return this
        return copy(
            value = LceState.Content(
                FriendDetailsContent(
                    user = current.user.withFriendship(isFriend),
                    posts = current.posts,
                    profile = current.profile
                )
            )
        )
    }

    private fun updatePost(
        prevState: FriendDetailsState,
        partialState: FriendDetailsPartialState.PostUpdated
    ): FriendDetailsState {
        val current = (prevState.value as? LceState.Content)?.data ?: return prevState
        current.posts.firstOrNull { it.id == partialState.postId }?.let { post ->
            partialState.likesCount?.let { post.likesCount = it }
            partialState.likedByMe?.let { post.likedByMe = it }
            partialState.viewsCount?.let { post.viewsCount = it }
            partialState.commentsCount?.let { post.commentsCount = it }
        }
        return prevState.copy(
            value = LceState.Content(
                FriendDetailsContent(
                    user = current.user,
                    posts = current.posts.toList(),
                    profile = current.profile
                )
            )
        )
    }

    private fun NewsCommunityFullDomainModel.withFriendship(isFriend: Boolean): NewsCommunityFullDomainModel {
        val wasFriend = role is RoleInCommunityTag.Subscribed
        val updatedSubscribers = when {
            isFriend && !wasFriend -> subscribers + id
            !isFriend && wasFriend -> subscribers.dropLast(1)
            else -> subscribers
        }
        return NewsCommunityFullDomainModel(
            id = id,
            ownerId = ownerId,
            subscribers = updatedSubscribers,
            title = title,
            description = description,
            avatar = avatar,
            role = if (isFriend) RoleInCommunityTag.Subscribed else RoleInCommunityTag.Unsubscribed,
            background = background
        )
    }
}
