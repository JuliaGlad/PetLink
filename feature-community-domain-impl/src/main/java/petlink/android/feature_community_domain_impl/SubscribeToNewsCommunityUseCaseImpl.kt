package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import javax.inject.Inject

class SubscribeToNewsCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): SubscribeToNewsCommunityUseCase {
    override suspend fun invoke(id: String) {
        repository.subscribeToCommunity(id)
    }
}