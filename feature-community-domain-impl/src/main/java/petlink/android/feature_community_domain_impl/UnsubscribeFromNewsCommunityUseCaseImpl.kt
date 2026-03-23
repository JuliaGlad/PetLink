package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import javax.inject.Inject

class UnsubscribeFromNewsCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): UnsubscribeFromNewsCommunityUseCase {
    override suspend fun invoke(id: String) {
        repository.unsubscribeFromCommunity(id)
    }
}