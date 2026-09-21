package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsCommunityDomainModel

interface GetUsersByIdsUseCase {
    suspend fun invoke(ids: List<String>): List<NewsCommunityDomainModel>
}
