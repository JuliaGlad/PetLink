package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.NewsPostDomain

interface GetCommunityPostsUseCase {
    suspend fun invoke(
        communityId: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    ): List<NewsPostDomain>
}
