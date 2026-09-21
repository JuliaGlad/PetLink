package petlink.android.feature_community_ui_create_post.comments.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain
import petlink.android.feature_profile_domain.usecase.user_account.AddUserPostCommentUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostCommentsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostCommentLikeUseCase

class UserPostCommentsActor(
    private val getUserPostCommentsUseCase: GetUserPostCommentsUseCase,
    private val addUserPostCommentUseCase: AddUserPostCommentUseCase,
    private val toggleUserPostCommentLikeUseCase: ToggleUserPostCommentLikeUseCase
) : MviActor<
        UserPostCommentsPartialState,
        UserPostCommentsIntent,
        UserPostCommentsState,
        UserPostCommentsEffect>() {
    override fun resolve(
        intent: UserPostCommentsIntent,
        state: UserPostCommentsState
    ): Flow<UserPostCommentsPartialState> =
        when (intent) {
            is UserPostCommentsIntent.LoadComments -> loadComments(intent.userId, intent.postId)
            is UserPostCommentsIntent.AddComment -> addComment(
                userId = intent.userId,
                postId = intent.postId,
                text = intent.text,
                parentId = intent.parentId,
                photos = intent.photos
            )
            is UserPostCommentsIntent.ToggleLike -> toggleLike(
                userId = intent.userId,
                postId = intent.postId,
                commentId = intent.commentId
            )
        }

    private fun loadComments(userId: String, postId: String) = flow {
        emit(UserPostCommentsPartialState.Loading)
        runCatching {
            getComments(userId, postId)
        }.fold(
            onSuccess = { emit(UserPostCommentsPartialState.DataLoaded(it)) },
            onFailure = { emit(UserPostCommentsPartialState.Error(it)) }
        )
    }

    private fun addComment(
        userId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>
    ) = flow {
        runCatching {
            addCommentUseCase(userId, postId, text, parentId, photos)
        }.fold(
            onSuccess = { emit(UserPostCommentsPartialState.CommentAdded(it)) },
            onFailure = { }
        )
    }

    private fun toggleLike(
        userId: String,
        postId: String,
        commentId: String
    ) = flow {
        runCatching {
            toggleLikeUseCase(userId, postId, commentId)
        }.fold(
            onSuccess = { emit(UserPostCommentsPartialState.CommentUpdated(it)) },
            onFailure = { }
        )
    }

    private suspend fun getComments(userId: String, postId: String): List<UserPostCommentDomain> =
        runCatchingNonCancellation {
            asyncAwait({ getUserPostCommentsUseCase.invoke(userId, postId) }) { it }
        }.getOrThrow()

    private suspend fun addCommentUseCase(
        userId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>
    ): UserPostCommentDomain =
        runCatchingNonCancellation {
            asyncAwait({
                addUserPostCommentUseCase.invoke(userId, postId, text, parentId, photos)
            }) { it }
        }.getOrThrow()

    private suspend fun toggleLikeUseCase(
        userId: String,
        postId: String,
        commentId: String
    ): UserPostCommentDomain =
        runCatchingNonCancellation {
            asyncAwait({
                toggleUserPostCommentLikeUseCase.invoke(userId, postId, commentId)
            }) { it }
        }.getOrThrow()
}
