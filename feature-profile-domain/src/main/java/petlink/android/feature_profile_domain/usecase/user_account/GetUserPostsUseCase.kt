package petlink.android.feature_profile_domain.usecase.user_account

import petlink.android.feature_profile_domain.model.user_account.UserPostDomain

interface GetUserPostsUseCase {
    suspend fun invoke(): List<UserPostDomain>
    suspend fun invoke(userId: String): List<UserPostDomain>
}
