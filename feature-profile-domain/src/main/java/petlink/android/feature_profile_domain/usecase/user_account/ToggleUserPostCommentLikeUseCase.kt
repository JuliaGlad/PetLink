package petlink.android.feature_profile_domain.usecase.user_account

import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain

interface ToggleUserPostCommentLikeUseCase {
    suspend fun invoke(
        userId: String,
        postId: String,
        commentId: String
    ): UserPostCommentDomain
}
