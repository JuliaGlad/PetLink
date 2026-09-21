package petlink.android.feature_community_ui_create_post.comments.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain

class UserPostCommentsReducer : MviReducer<UserPostCommentsPartialState, UserPostCommentsState> {
    override fun reduce(
        prevState: UserPostCommentsState,
        partialState: UserPostCommentsPartialState
    ): UserPostCommentsState =
        when (partialState) {
            UserPostCommentsPartialState.Loading ->
                prevState.copy(value = LceState.Loading, commentJustAdded = false)
            is UserPostCommentsPartialState.DataLoaded ->
                prevState.copy(value = LceState.Content(partialState.comments), commentJustAdded = false)
            is UserPostCommentsPartialState.CommentAdded -> {
                val comments = (prevState.value as? LceState.Content)?.data.orEmpty().toMutableList()
                comments.add(partialState.comment)
                prevState.copy(value = LceState.Content(comments), commentJustAdded = true)
            }
            is UserPostCommentsPartialState.CommentUpdated -> {
                val comments = (prevState.value as? LceState.Content)?.data.orEmpty().toMutableList()
                val index = comments.indexOfFirst { it.id == partialState.comment.id }
                if (index >= 0) {
                    comments[index] = partialState.comment
                }
                prevState.copy(value = LceState.Content(comments), commentJustAdded = false)
            }
            is UserPostCommentsPartialState.Error ->
                prevState.copy(value = LceState.Error(partialState.throwable), commentJustAdded = false)
        }
}
