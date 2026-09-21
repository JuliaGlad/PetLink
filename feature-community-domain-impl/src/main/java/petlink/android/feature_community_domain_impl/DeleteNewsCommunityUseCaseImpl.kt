package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.DeleteNewsCommunityUseCase
import javax.inject.Inject

class DeleteNewsCommunityUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : DeleteNewsCommunityUseCase {
    override suspend fun invoke(id: String, type: CommunitiesTypeTag) {
        router.get(type).deleteCommunity(id)
    }
}
