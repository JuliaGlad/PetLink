package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.PostCommentDomain

interface GetPostCommentsUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    ): List<PostCommentDomain>
}
