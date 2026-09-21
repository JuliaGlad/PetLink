package petlink.android.feature_community_friend_details.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.GetUserByIdUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import petlink.android.feature_community_friend_details.fragment.model.FriendDetailsContent
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase

class FriendDetailsActor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val removeFriendUseCase: RemoveFriendUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
    private val toggleUserPostLikeUseCase: ToggleUserPostLikeUseCase,
    private val markUserPostViewedUseCase: MarkUserPostViewedUseCase
) : MviActor<
        FriendDetailsPartialState,
        FriendDetailsIntent,
        FriendDetailsState,
        FriendDetailsEffect>() {
    override fun resolve(
        intent: FriendDetailsIntent,
        state: FriendDetailsState
    ): Flow<FriendDetailsPartialState> =
        when (intent) {
            is FriendDetailsIntent.LoadUser -> loadUser(intent.userId)
            is FriendDetailsIntent.AddFriend -> addFriend(intent.userId)
            is FriendDetailsIntent.RemoveFriend -> removeFriend(intent.userId)
            is FriendDetailsIntent.TogglePostLike -> toggleLike(intent.userId, intent.postId)
            is FriendDetailsIntent.MarkPostViewed -> markViewed(intent.userId, intent.postId)
            is FriendDetailsIntent.CommentAdded -> updateCommentsCount(state, intent.postId)
        }

    private fun loadUser(userId: String) =
        flow {
            emit(FriendDetailsPartialState.Loading)
            runCatching {
                loadContent(userId)
            }.fold(
                onSuccess = { content ->
                    emit(FriendDetailsPartialState.DataLoaded(content))
                },
                onFailure = { throwable ->
                    emit(FriendDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun addFriend(userId: String) =
        flow {
            runCatching {
                addFriendUseCase.invoke(userId)
            }.fold(
                onSuccess = { emit(FriendDetailsPartialState.FriendAdded) },
                onFailure = { throwable -> emit(FriendDetailsPartialState.Error(throwable)) }
            )
        }

    private fun removeFriend(userId: String) =
        flow {
            runCatching {
                removeFriendUseCase.invoke(userId)
            }.fold(
                onSuccess = { emit(FriendDetailsPartialState.FriendRemoved) },
                onFailure = { throwable -> emit(FriendDetailsPartialState.Error(throwable)) }
            )
        }

    private fun toggleLike(userId: String, postId: String) = flow {
        if (userId.isBlank() || postId.isBlank()) return@flow
        runCatching {
            toggleLikeUseCase(userId, postId)
        }.fold(
            onSuccess = { post ->
                emit(
                    FriendDetailsPartialState.PostUpdated(
                        postId = post.id,
                        likesCount = post.likesCount,
                        likedByMe = post.likedByMe
                    )
                )
            },
            onFailure = { }
        )
    }

    private fun markViewed(userId: String, postId: String) = flow {
        if (userId.isBlank() || postId.isBlank()) return@flow
        runCatching {
            markViewedUseCase(userId, postId)
        }.fold(
            onSuccess = { post ->
                emit(
                    FriendDetailsPartialState.PostUpdated(
                        postId = post.id,
                        viewsCount = post.viewsCount
                    )
                )
            },
            onFailure = { }
        )
    }

    private fun updateCommentsCount(state: FriendDetailsState, postId: String) = flow {
        val post = (state.value as? LceState.Content)?.data?.posts?.firstOrNull { it.id == postId }
        emit(
            FriendDetailsPartialState.PostUpdated(
                postId = postId,
                commentsCount = (post?.commentsCount ?: 0) + 1
            )
        )
    }

    private suspend fun loadContent(userId: String): FriendDetailsContent =
        runCatchingNonCancellation {
            asyncAwait(
                { getUserByIdUseCase.invoke(userId) },
                { getUserPostsUseCase.invoke(userId) }
            ) { user, posts ->
                FriendDetailsContent(
                    user = user,
                    posts = posts,
                    profile = getUserFullDataUseCase.invoke(userId)
                )
            }
        }.getOrThrow()

    private suspend fun toggleLikeUseCase(userId: String, postId: String): UserPostDomain =
        runCatchingNonCancellation {
            asyncAwait({ toggleUserPostLikeUseCase.invoke(userId, postId) }) { it }
        }.getOrThrow()

    private suspend fun markViewedUseCase(userId: String, postId: String): UserPostDomain =
        runCatchingNonCancellation {
            asyncAwait({ markUserPostViewedUseCase.invoke(userId, postId) }) { it }
        }.getOrThrow()
}
