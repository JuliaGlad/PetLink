package petlink.android.feature_community_ui_news_details.comments.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.PostCommentDomain
import petlink.android.feature_community_domain.usecase.AddPostCommentUseCase
import petlink.android.feature_community_domain.usecase.GetPostCommentsUseCase
import petlink.android.feature_community_domain.usecase.ToggleCommentLikeUseCase

class PostCommentsActor(
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val addPostCommentUseCase: AddPostCommentUseCase,
    private val toggleCommentLikeUseCase: ToggleCommentLikeUseCase
) : MviActor<
        PostCommentsPartialState,
        PostCommentsIntent,
        PostCommentsState,
        PostCommentsEffect>() {
    override fun resolve(
        intent: PostCommentsIntent,
        state: PostCommentsState
    ): Flow<PostCommentsPartialState> =
        when (intent) {
            is PostCommentsIntent.LoadComments -> loadComments(
                communityId = intent.communityId,
                postId = intent.postId,
                communityType = intent.communityType
            )
            is PostCommentsIntent.AddComment -> addComment(
                communityId = intent.communityId,
                postId = intent.postId,
                text = intent.text,
                parentId = intent.parentId,
                photos = intent.photos,
                communityType = intent.communityType
            )
            is PostCommentsIntent.ToggleLike -> toggleLike(
                communityId = intent.communityId,
                postId = intent.postId,
                commentId = intent.commentId,
                communityType = intent.communityType
            )
        }

    private fun loadComments(
        communityId: String,
        postId: String,
        communityType: CommunitiesTypeTag
    ) =
        flow {
            emit(PostCommentsPartialState.Loading)
            runCatching {
                getPostComments(communityId, postId, communityType)
            }.fold(
                onSuccess = { comments ->
                    emit(PostCommentsPartialState.DataLoaded(comments))
                },
                onFailure = { throwable ->
                    emit(PostCommentsPartialState.Error(throwable))
                }
            )
        }

    private fun addComment(
        communityId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>,
        communityType: CommunitiesTypeTag
    ) = flow {
        runCatching {
            addPostComment(communityId, postId, text, parentId, photos, communityType)
        }.fold(
            onSuccess = { comment ->
                emit(PostCommentsPartialState.CommentAdded(comment))
            },
            onFailure = { }
        )
    }

    private fun toggleLike(
        communityId: String,
        postId: String,
        commentId: String,
        communityType: CommunitiesTypeTag
    ) = flow {
        runCatching {
            toggleCommentLike(communityId, postId, commentId, communityType)
        }.fold(
            onSuccess = { comment ->
                emit(PostCommentsPartialState.CommentUpdated(comment))
            },
            onFailure = { }
        )
    }

    private suspend fun getPostComments(
        communityId: String,
        postId: String,
        communityType: CommunitiesTypeTag
    ): List<PostCommentDomain> =
        runCatchingNonCancellation {
            asyncAwait({
                getPostCommentsUseCase.invoke(communityId, postId, communityType)
            }) { it }
        }.getOrThrow()

    private suspend fun addPostComment(
        communityId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>,
        communityType: CommunitiesTypeTag
    ): PostCommentDomain =
        runCatchingNonCancellation {
            asyncAwait({
                addPostCommentUseCase.invoke(
                    communityId,
                    postId,
                    text,
                    parentId,
                    photos,
                    communityType
                )
            }) { it }
        }.getOrThrow()

    private suspend fun toggleCommentLike(
        communityId: String,
        postId: String,
        commentId: String,
        communityType: CommunitiesTypeTag
    ): PostCommentDomain =
        runCatchingNonCancellation {
            asyncAwait({
                toggleCommentLikeUseCase.invoke(communityId, postId, commentId, communityType)
            }) { it }
        }.getOrThrow()
}
