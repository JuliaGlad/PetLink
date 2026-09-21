package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.PostCommentDomain

interface AddPostCommentUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String> = emptyList(),
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    ): PostCommentDomain
}
