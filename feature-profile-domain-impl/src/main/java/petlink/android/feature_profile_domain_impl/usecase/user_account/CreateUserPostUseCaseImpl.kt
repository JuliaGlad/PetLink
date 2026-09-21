package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_domain.usecase.user_account.CreateUserPostUseCase
import javax.inject.Inject

class CreateUserPostUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
) : CreateUserPostUseCase {
    override suspend fun invoke(
        title: String,
        description: String,
        photos: List<String>
    ): UserPostDomain =
        repository.createPost(title, description, photos).toDomain()
}
