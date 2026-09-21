package petlink.android.feature_community_ui_news_details.comments.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_community_domain.model.PostCommentDomain

sealed interface PostCommentsPartialState : MviPartialState {

    data object Loading : PostCommentsPartialState

    class DataLoaded(val comments: List<PostCommentDomain>) : PostCommentsPartialState

    class CommentAdded(val comment: PostCommentDomain) : PostCommentsPartialState

    class CommentUpdated(val comment: PostCommentDomain) : PostCommentsPartialState

    class Error(val throwable: Throwable) : PostCommentsPartialState
}
