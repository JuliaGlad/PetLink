package petlink.android.feature_community_ui_create_post.comments.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain

sealed interface UserPostCommentsPartialState : MviPartialState {

    data object Loading : UserPostCommentsPartialState

    class DataLoaded(val comments: List<UserPostCommentDomain>) : UserPostCommentsPartialState

    class CommentAdded(val comment: UserPostCommentDomain) : UserPostCommentsPartialState

    class CommentUpdated(val comment: UserPostCommentDomain) : UserPostCommentsPartialState

    class Error(val throwable: Throwable) : UserPostCommentsPartialState
}
