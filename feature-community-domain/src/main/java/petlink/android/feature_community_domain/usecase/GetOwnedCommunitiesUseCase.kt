package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel

interface GetOwnedCommunitiesUseCase {
    suspend fun invoke(type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag): List<NewsCommunityDomainModel>
}