package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import javax.inject.Inject

class GetSubscribedCommunitiesUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): GetSubscribedCommunitiesUseCase {
    override suspend fun invoke(): List<NewsCommunityDomainModel> =
        repository.getSubscribedCommunities().map { it.toDomain() }.toList()
}