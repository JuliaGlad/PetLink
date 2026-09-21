package petlink.android.feature_community_ui_news_details.comments.di

import petlink.android.feature_community_domain.usecase.AddPostCommentUseCase
import petlink.android.feature_community_domain.usecase.GetPostCommentsUseCase
import petlink.android.feature_community_domain.usecase.ToggleCommentLikeUseCase
import petlink.android.feature_community_ui_news_details.comments.mvi.PostCommentsActor
import petlink.android.feature_community_ui_news_details.comments.mvi.PostCommentsReducer
import javax.inject.Inject

class PostCommentsLocalDi @Inject constructor(
    getPostCommentsUseCase: GetPostCommentsUseCase,
    addPostCommentUseCase: AddPostCommentUseCase,
    toggleCommentLikeUseCase: ToggleCommentLikeUseCase
) {

    val actor by lazy {
        PostCommentsActor(
            getPostCommentsUseCase = getPostCommentsUseCase,
            addPostCommentUseCase = addPostCommentUseCase,
            toggleCommentLikeUseCase = toggleCommentLikeUseCase
        )
    }

    val reducer by lazy { PostCommentsReducer() }
}
