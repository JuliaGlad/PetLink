package petlink.android.feature_community_ui_news_details.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.GetUsersByIdsUseCase
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import petlink.android.feature_community_ui_news_details.fragment.mapper.toUi
import petlink.android.feature_community_ui_news_details.fragment.model.CommunitiesContent
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel
import petlink.android.feature_community_ui_news_details.fragment.model.SubscriberUi

class CommunityDetailsActor(
    private val getNewsCommunityByIdUseCase: GetNewsCommunityByIdUseCase,
    private val subscribeToNewsCommunityUseCase: SubscribeToNewsCommunityUseCase,
    private val unsubscribeFromNewsCommunityUseCase: UnsubscribeFromNewsCommunityUseCase,
    private val updateNewsCommunityAvatarUseCase: UpdateNewsCommunityAvatarUseCase,
    private val updateNewsCommunityBackgroundUseCase: UpdateNewsCommunityBackgroundUseCase,
    private val getCommunityPostsUseCase: GetCommunityPostsUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val togglePostLikeUseCase: TogglePostLikeUseCase,
    private val markPostViewedUseCase: MarkPostViewedUseCase
) : MviActor<
        CommunityDetailsPartialState,
        CommunityDetailsIntent,
        CommunityDetailsState,
        CommunityDetailsEffect>() {
    override fun resolve(
        intent: CommunityDetailsIntent,
        state: CommunityDetailsState
    ): Flow<CommunityDetailsPartialState> =
        when (intent) {
            is CommunityDetailsIntent.GetCommunityDetails -> getNewsCommunityData(
                communityId = intent.id,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.Subscribe -> subscribeToNewsCommunity(
                communityId = intent.id,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.Unsubscribe -> unsubscribeFromNewsCommunity(
                communityId = intent.id,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.UpdateAvatar -> updateAvatar(
                communityId = intent.id,
                newUri = intent.uri,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.UpdateBackground -> updateBackground(
                communityId = intent.id,
                newUri = intent.uri,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.CreatePost -> createPost(
                communityId = intent.communityId,
                title = intent.title,
                description = intent.description,
                photos = intent.photos,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.TogglePostLike -> toggleLike(
                communityId = (state.value as? LceState.Content)?.data?.communityId.orEmpty(),
                postId = intent.postId,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.MarkPostViewed -> updatePostViews(
                communityId = (state.value as? LceState.Content)?.data?.communityId.orEmpty(),
                postId = intent.postId,
                type = intent.communityTypeTag
            )

            is CommunityDetailsIntent.CommentAdded -> updateCommentsCount(state, intent.postId)
        }

    private fun createPost(
        communityId: String,
        title: String,
        description: String,
        photos: List<String>,
        type: CommunitiesTypeTag
    ) = flow {
        runCatching {
            createPostUseCase(
                communityId = communityId,
                title = title,
                description = description,
                photos = photos,
                type = type
            )
        }.fold(
            onSuccess = { post ->
                emit(
                    CommunityDetailsPartialState.PostCreated(
                        id = post.id,
                        title = post.title,
                        description = post.description,
                        photos = post.photos
                    )
                )
            },
            onFailure = { }
        )
    }

    private suspend fun createPostUseCase(
        communityId: String,
        title: String,
        description: String,
        photos: List<String>,
        type: CommunitiesTypeTag
    ) = runCatchingNonCancellation {
        asyncAwait({
            createPostUseCase.invoke(
                communityId = communityId,
                postId = "",
                title = title,
                description = description,
                photos = photos,
                type = type
            )
        }) { it }
    }.getOrThrow()

    private fun toggleLike(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) = flow {
        if (communityId.isBlank() || postId.isBlank()) return@flow
        runCatching {
            togglePostLikeUseCase(communityId, postId, type)
        }.fold(
            onSuccess = { post ->
                emit(
                    CommunityDetailsPartialState.PostUpdated(
                        postId = post.id,
                        likesCount = post.likesCount,
                        likedByMe = post.likedByMe
                    )
                )
            },
            onFailure = { }
        )
    }

    private suspend fun togglePostLikeUseCase(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                togglePostLikeUseCase.invoke(communityId, postId, type)
            }) { it }
        }.getOrThrow()

    private fun updatePostViews(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) = flow {
        if (communityId.isBlank() || postId.isBlank()) return@flow
        runCatching {
            markPostViewedUseCase(communityId, postId, type)
        }.fold(
            onSuccess = { post ->
                emit(
                    CommunityDetailsPartialState.PostUpdated(
                        postId = post.id,
                        viewsCount = post.viewsCount
                    )
                )
            },
            onFailure = { }
        )
    }

    private suspend fun markPostViewedUseCase(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                markPostViewedUseCase.invoke(communityId, postId, type)
            }) { it }
        }.getOrThrow()

    private fun updateCommentsCount(state: CommunityDetailsState, postId: String) = flow {
        val post = (state.value as? LceState.Content)
            ?.data
            ?.content
            ?.filterIsInstance<CommunitiesContent.Post>()
            ?.firstOrNull { it.id == postId }
        emit(
            CommunityDetailsPartialState.PostUpdated(
                postId = postId,
                commentsCount = (post?.commentsCount ?: 0) + 1
            )
        )
    }

    private fun updateBackground(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag
    ) =
        flow {
            runCatching {
                updateBackgroundUseCase(communityId, newUri, type)
            }.fold(
                onSuccess = {
                    emit(CommunityDetailsPartialState.BackgroundUpdated(newUri))
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun updateAvatar(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag
    ) =
        flow {
            runCatching {
                updateAvatarUseCase(communityId, newUri, type)
            }.fold(
                onSuccess = {
                    emit(CommunityDetailsPartialState.AvatarUpdated(newUri))
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun updateBackgroundUseCase(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                updateNewsCommunityBackgroundUseCase.invoke(
                    communityId = communityId,
                    newUri = newUri,
                    type = type
                )
            }) { it }
        }.getOrThrow()

    private suspend fun updateAvatarUseCase(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                updateNewsCommunityAvatarUseCase.invoke(
                    communityId = communityId,
                    newUri = newUri,
                    type = type
                )
            }) { it }
        }.getOrThrow()

    private fun subscribeToNewsCommunity(
        communityId: String,
        type: CommunitiesTypeTag
    ) =
        flow {
            runCatching {
                subscribeToNewsCommunityUseCase(communityId, type)
            }.fold(
                onSuccess = {
                    emit(CommunityDetailsPartialState.Subscribed)
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun unsubscribeFromNewsCommunity(
        communityId: String,
        type: CommunitiesTypeTag
    ) =
        flow {
            runCatching {
                unsubscribeFromNewsCommunityUseCase(communityId, type)
            }.fold(
                onSuccess = {
                    emit(CommunityDetailsPartialState.Unsubscribed)
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun unsubscribeFromNewsCommunityUseCase(
        communityId: String,
        type: CommunitiesTypeTag
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                unsubscribeFromNewsCommunityUseCase.invoke(communityId, type)
            }) { it }
        }.getOrThrow()

    private suspend fun subscribeToNewsCommunityUseCase(
        communityId: String,
        type: CommunitiesTypeTag
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                subscribeToNewsCommunityUseCase.invoke(communityId, type)
            }) { it }
        }.getOrThrow()

    private fun getNewsCommunityData(
        communityId: String,
        type: CommunitiesTypeTag
    ) =
        flow<CommunityDetailsPartialState> {
            emit(CommunityDetailsPartialState.Loading)
            runCatching {
                loadNewsCommunityById(communityId, type)
            }.fold(
                onSuccess = { data ->
                    emit(CommunityDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun loadNewsCommunityById(
        communityId: String,
        type: CommunitiesTypeTag
    ): CommunityUiModel =
        runCatchingNonCancellation {
            asyncAwait(
                { getNewsCommunityByIdUseCase.invoke(communityId, type) }
            ) { data ->
                val model = data.toUi()
                val posts = getCommunityPostsUseCase.invoke(communityId, type)
                model.content.addAll(
                    posts.map { post ->
                        CommunitiesContent.Post(
                            id = post.id,
                            title = post.title,
                            description = post.description,
                            photos = post.photos,
                            likesCount = post.likesCount,
                            likedByMe = post.likedByMe,
                            commentsCount = post.commentsCount,
                            viewsCount = post.viewsCount
                        )
                    }
                )
                if (data.subscribers.isNotEmpty()) {
                    model.subscribers = getUsersByIdsUseCase.invoke(data.subscribers)
                        .map { SubscriberUi(it.id, it.title, it.avatar) }
                }
                model
            }
        }.getOrThrow()
}
