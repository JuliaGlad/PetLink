package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import javax.inject.Inject

class GetSubscribedCommunitiesUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : GetSubscribedCommunitiesUseCase {
    override suspend fun invoke(type: CommunitiesTypeTag): List<NewsCommunityDomainModel> =
        router.get(type).getSubscribedCommunities().map { it.toDomain() }
}
