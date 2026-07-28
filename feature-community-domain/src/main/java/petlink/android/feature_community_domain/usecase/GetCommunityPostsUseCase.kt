package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsPostDomain

interface GetCommunityPostsUseCase {
    suspend fun invoke(communityId: String): List<NewsPostDomain>
}