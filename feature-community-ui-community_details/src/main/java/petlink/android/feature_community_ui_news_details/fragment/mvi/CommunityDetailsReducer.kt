package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_ui_news_details.fragment.model.CommunitiesContent
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel

class CommunityDetailsReducer: MviReducer<
        CommunityDetailsPartialState,
        CommunityDetailsState> {
    override fun reduce(
        prevState: CommunityDetailsState,
        partialState: CommunityDetailsPartialState
    ): CommunityDetailsState =
        when(partialState){
            is CommunityDetailsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.model)
            is CommunityDetailsPartialState.Error -> updateError(prevState, partialState.throwable)
            CommunityDetailsPartialState.Loading -> updateLoading(prevState)
            is CommunityDetailsPartialState.AvatarUpdated -> updateDataLoadedWithAvatarUpdated(prevState=prevState, newUri = partialState.newUri)
            is CommunityDetailsPartialState.BackgroundUpdated -> updateDataLoadedWithBackgroundUpdated(prevState=prevState, newUri = partialState.newUri)
            CommunityDetailsPartialState.Subscribed -> updateSubscribe(prevState)
            CommunityDetailsPartialState.Unsubscribed -> updateUnsubscribe(prevState)
            is CommunityDetailsPartialState.PostCreated -> updatePostCreated(prevState, partialState)
            is CommunityDetailsPartialState.PostUpdated -> updatePost(prevState, partialState)
        }

    private fun updateSubscribe(prevState: CommunityDetailsState): CommunityDetailsState {
        val content = (prevState.value as? LceState.Content)?.data ?: return prevState
        content.role = RoleInCommunityTag.Subscribed
        content.subscribersCount += 1
        return updateDataLoaded(prevState, content)
    }

    private fun updateUnsubscribe(prevState: CommunityDetailsState): CommunityDetailsState {
        val content = (prevState.value as? LceState.Content)?.data ?: return prevState
        content.role = RoleInCommunityTag.Unsubscribed
        content.subscribersCount = (content.subscribersCount - 1).coerceAtLeast(0)
        return updateDataLoaded(prevState, content)
    }

    private fun updatePostCreated(
        prevState: CommunityDetailsState,
        partialState: CommunityDetailsPartialState.PostCreated
    ): CommunityDetailsState {
        val content = (prevState.value as? LceState.Content)?.data ?: return prevState
        val posts = content.content.filterIsInstance<CommunitiesContent.Post>()
        val alreadyAdded = posts.any {
            it.title == partialState.title &&
                it.description == partialState.description &&
                it.photos == partialState.photos
        }
        if (!alreadyAdded) {
            content.content.add(
                0,
                CommunitiesContent.Post(
                    id = partialState.id,
                    title = partialState.title,
                    description = partialState.description,
                    photos = partialState.photos
                )
            )
        } else {
            posts.firstOrNull {
                it.title == partialState.title &&
                    it.description == partialState.description &&
                    it.photos == partialState.photos
            }?.let { post ->
                content.content[content.content.indexOf(post)] = CommunitiesContent.Post(
                    id = partialState.id,
                    title = post.title,
                    description = post.description,
                    photos = post.photos,
                    likesCount = post.likesCount,
                    likedByMe = post.likedByMe,
                    commentsCount = post.commentsCount,
                    viewsCount = post.viewsCount
                )
            }
        }
        return updateDataLoaded(prevState, content)
    }

    private fun updatePost(
        prevState: CommunityDetailsState,
        partialState: CommunityDetailsPartialState.PostUpdated
    ): CommunityDetailsState {
        val content = (prevState.value as? LceState.Content)?.data ?: return prevState
        content.content.forEach { item ->
            if (item is CommunitiesContent.Post && item.id == partialState.postId) {
                partialState.likesCount?.let { item.likesCount = it }
                partialState.likedByMe?.let { item.likedByMe = it }
                partialState.commentsCount?.let { item.commentsCount = it }
                partialState.viewsCount?.let { item.viewsCount = it }
            }
        }
        return updateDataLoaded(prevState, content)
    }

    private fun updateLoading(prevState: CommunityDetailsState) =
        prevState.copy(value = LceState.Loading)

    private fun updateError(prevState: CommunityDetailsState, error: Throwable) =
        prevState.copy(value = LceState.Error(error))

    private fun updateDataLoaded(prevState: CommunityDetailsState, model: CommunityUiModel) =
        prevState.copy(
            value = LceState.Content(
                CommunityUiModel(
                    communityId = model.communityId,
                    title = model.title,
                    description = model.description,
                    subscribersCount = model.subscribersCount,
                    role = model.role,
                    avatar = model.avatar,
                    background = model.background,
                    subscriberIds = model.subscriberIds,
                    subscribers = model.subscribers,
                    content = model.content
                )
            )
        )

    private fun updateDataLoadedWithAvatarUpdated(prevState: CommunityDetailsState, newUri: String): CommunityDetailsState{
        val dataUpdated = (prevState.value as LceState.Content).data.apply { avatar = newUri }
        return prevState.copy(value = LceState.Content(dataUpdated))
    }

    private fun updateDataLoadedWithBackgroundUpdated(prevState: CommunityDetailsState, newUri: String): CommunityDetailsState{
        val dataUpdated = (prevState.value as LceState.Content).data.apply { background = newUri }
        return prevState.copy(value = LceState.Content(dataUpdated))
    }
}