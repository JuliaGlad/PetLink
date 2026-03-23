package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import javax.inject.Inject

class AddNewsCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): AddNewsCommunityUseCase {
    override suspend fun invoke(
        title: String,
        description: String,
        avatar: String
    ) {
        repository.addNewsCommunity(
            title = title,
            description = description,
            avatar = avatar
        )
    }
}