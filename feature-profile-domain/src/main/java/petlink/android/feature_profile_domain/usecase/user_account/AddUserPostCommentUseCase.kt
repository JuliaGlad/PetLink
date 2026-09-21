package petlink.android.feature_profile_domain.usecase.user_account

import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain

interface AddUserPostCommentUseCase {
    suspend fun invoke(
        userId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String> = emptyList()
    ): UserPostCommentDomain
}
