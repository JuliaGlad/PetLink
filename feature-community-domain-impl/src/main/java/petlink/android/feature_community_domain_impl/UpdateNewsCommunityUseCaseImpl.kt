package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import javax.inject.Inject

class UpdateNewsCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): UpdateNewsCommunityUseCase {
    override suspend fun invoke(
        id: String,
        newTitle: String?,
        newDescription: String?
    ) {
        repository.updateNewsCommunityData(
            id = id,
            newTitle = newTitle,
            newDescription = newDescription
        )
    }
}