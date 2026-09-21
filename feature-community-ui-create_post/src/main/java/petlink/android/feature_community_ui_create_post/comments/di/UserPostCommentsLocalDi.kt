package petlink.android.feature_community_ui_create_post.comments.di

import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsActor
import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsReducer
import petlink.android.feature_profile_domain.usecase.user_account.AddUserPostCommentUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostCommentsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostCommentLikeUseCase
import javax.inject.Inject

class UserPostCommentsLocalDi @Inject constructor(
    getUserPostCommentsUseCase: GetUserPostCommentsUseCase,
    addUserPostCommentUseCase: AddUserPostCommentUseCase,
    toggleUserPostCommentLikeUseCase: ToggleUserPostCommentLikeUseCase
) {
    val actor by lazy {
        UserPostCommentsActor(
            getUserPostCommentsUseCase = getUserPostCommentsUseCase,
            addUserPostCommentUseCase = addUserPostCommentUseCase,
            toggleUserPostCommentLikeUseCase = toggleUserPostCommentLikeUseCase
        )
    }

    val reducer by lazy { UserPostCommentsReducer() }
}
