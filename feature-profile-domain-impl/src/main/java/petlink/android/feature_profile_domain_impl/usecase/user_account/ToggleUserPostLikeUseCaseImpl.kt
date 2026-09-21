package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase
import javax.inject.Inject

class ToggleUserPostLikeUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
) : ToggleUserPostLikeUseCase {
    override suspend fun invoke(userId: String, postId: String): UserPostDomain =
        repository.togglePostLike(userId, postId).toDomain()
}
