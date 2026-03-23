package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsCommunityDomainModel

interface GetNewsCommunityByIdUseCase {
    suspend fun invoke(id: String): NewsCommunityDomainModel
}