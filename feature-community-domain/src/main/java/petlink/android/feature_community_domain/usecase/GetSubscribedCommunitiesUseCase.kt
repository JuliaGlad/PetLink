package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsCommunityDomainModel

interface GetSubscribedCommunitiesUseCase {
    suspend fun invoke(): List<NewsCommunityDomainModel>
}