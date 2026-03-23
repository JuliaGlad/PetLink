package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import javax.inject.Inject

class GetNewsCommunityByIdUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): GetNewsCommunityByIdUseCase {
    override suspend fun invoke(id: String): NewsCommunityDomainModel =
        repository.getNewsCommunity().filter { it.id == id }.map { it.toDomain() }[0]
}