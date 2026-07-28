package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsPostDomain

interface GetPostByIdUseCase {
    suspend fun invoke(
        postId: String,
        communityId: String
    ): NewsPostDomain
}