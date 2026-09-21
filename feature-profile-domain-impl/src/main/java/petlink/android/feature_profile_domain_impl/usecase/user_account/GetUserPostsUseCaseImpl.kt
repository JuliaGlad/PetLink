package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import javax.inject.Inject

class GetUserPostsUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
) : GetUserPostsUseCase {
    override suspend fun invoke(): List<UserPostDomain> =
        repository.getPosts().map { it.toDomain() }

    override suspend fun invoke(userId: String): List<UserPostDomain> =
        repository.getPosts(userId).map { it.toDomain() }
}
