package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostCommentsUseCase
import javax.inject.Inject

class GetUserPostCommentsUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
) : GetUserPostCommentsUseCase {
    override suspend fun invoke(userId: String, postId: String): List<UserPostCommentDomain> =
        repository.getPostComments(userId, postId).map { it.toDomain() }
}
