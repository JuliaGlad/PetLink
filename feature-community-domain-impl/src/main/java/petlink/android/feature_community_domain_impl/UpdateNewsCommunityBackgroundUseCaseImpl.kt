package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import javax.inject.Inject

class UpdateNewsCommunityBackgroundUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): UpdateNewsCommunityBackgroundUseCase {
    override suspend fun invoke(communityId: String, newUri: String) =
        repository.updateNewsCommunityBackground(
            id = communityId,
            newUri = newUri
        )
}