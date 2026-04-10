package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import javax.inject.Inject

class GetOwnedCommunitiesUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): GetOwnedCommunitiesUseCase {
    override suspend fun invoke(): List<NewsCommunityDomainModel> =
        repository.getOwnedCommunities().map { it.toDomain() }.toList()
}