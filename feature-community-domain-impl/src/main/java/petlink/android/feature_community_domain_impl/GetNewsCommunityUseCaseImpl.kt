package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import javax.inject.Inject

class GetNewsCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): GetNewsCommunityUseCase {
    override suspend fun invoke(): List<NewsCommunityDomainModel> =
        repository.getNewsCommunity().map { it.toDomain() }.toList()
}