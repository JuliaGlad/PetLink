package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import javax.inject.Inject

class SubscribeToNewsCommunityUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : SubscribeToNewsCommunityUseCase {
    override suspend fun invoke(id: String, type: CommunitiesTypeTag) {
        router.get(type).subscribeToCommunity(id)
    }
}
