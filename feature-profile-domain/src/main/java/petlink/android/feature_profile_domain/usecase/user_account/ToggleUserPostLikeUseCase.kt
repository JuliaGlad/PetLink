package petlink.android.feature_profile_domain.usecase.user_account

import petlink.android.feature_profile_domain.model.user_account.UserPostDomain

interface ToggleUserPostLikeUseCase {
    suspend fun invoke(userId: String, postId: String): UserPostDomain
}
