package petlink.android.feature_community_ui_news_details.comments.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_domain.model.PostCommentDomain

class PostCommentsReducer : MviReducer<PostCommentsPartialState, PostCommentsState> {
    override fun reduce(
        prevState: PostCommentsState,
        partialState: PostCommentsPartialState
    ): PostCommentsState =
        when (partialState) {
            PostCommentsPartialState.Loading -> updateLoading(prevState)
            is PostCommentsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.comments)
            is PostCommentsPartialState.CommentAdded -> updateCommentAdded(prevState, partialState.comment)
            is PostCommentsPartialState.CommentUpdated -> updateCommentUpdated(prevState, partialState.comment)
            is PostCommentsPartialState.Error -> updateError(prevState, partialState.throwable)
        }

    private fun updateLoading(prevState: PostCommentsState) =
        prevState.copy(value = LceState.Loading, commentJustAdded = false)

    private fun updateError(prevState: PostCommentsState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable), commentJustAdded = false)

    private fun updateDataLoaded(prevState: PostCommentsState, comments: List<PostCommentDomain>) =
        prevState.copy(value = LceState.Content(comments), commentJustAdded = false)

    private fun updateCommentAdded(prevState: PostCommentsState, comment: PostCommentDomain): PostCommentsState {
        val comments = (prevState.value as? LceState.Content)?.data.orEmpty().toMutableList()
        comments.add(comment)
        return prevState.copy(value = LceState.Content(comments), commentJustAdded = true)
    }

    private fun updateCommentUpdated(prevState: PostCommentsState, comment: PostCommentDomain): PostCommentsState {
        val comments = (prevState.value as? LceState.Content)?.data.orEmpty().toMutableList()
        val index = comments.indexOfFirst { it.id == comment.id }
        if (index >= 0) {
            comments[index] = comment
        }
        return prevState.copy(value = LceState.Content(comments), commentJustAdded = false)
    }
}
