package petlink.android.feature_profile_domain.usecase.user_account

import petlink.android.feature_profile_domain.model.user_account.UserPostDomain

interface CreateUserPostUseCase {
    suspend fun invoke(
        title: String,
        description: String,
        photos: List<String>
    ): UserPostDomain
}
