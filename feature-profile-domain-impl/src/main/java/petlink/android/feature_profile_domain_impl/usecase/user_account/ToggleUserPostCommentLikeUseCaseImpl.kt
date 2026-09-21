package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostCommentLikeUseCase
import javax.inject.Inject

class ToggleUserPostCommentLikeUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
) : ToggleUserPostCommentLikeUseCase {
    override suspend fun invoke(
        userId: String,
        postId: String,
        commentId: String
    ): UserPostCommentDomain =
        repository.toggleCommentLike(userId, postId, commentId).toDomain()
}
