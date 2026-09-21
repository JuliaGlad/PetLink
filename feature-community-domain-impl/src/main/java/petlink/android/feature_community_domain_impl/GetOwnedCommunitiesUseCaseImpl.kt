package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import javax.inject.Inject

class GetOwnedCommunitiesUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : GetOwnedCommunitiesUseCase {
    override suspend fun invoke(type: CommunitiesTypeTag): List<NewsCommunityDomainModel> =
        router.get(type).getOwnedCommunities().map { it.toDomain() }
}
