package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel

interface GetNewsCommunityByIdUseCase {
    suspend fun invoke(id: String): NewsCommunityFullDomainModel
}