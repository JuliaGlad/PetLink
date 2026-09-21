package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.NewsPostDomain

interface TogglePostLikeUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    ): NewsPostDomain
}
