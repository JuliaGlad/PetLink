package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import javax.inject.Inject

class UnsubscribeFromNewsCommunityUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : UnsubscribeFromNewsCommunityUseCase {
    override suspend fun invoke(id: String, type: CommunitiesTypeTag) {
        router.get(type).unsubscribeFromCommunity(id)
    }
}
