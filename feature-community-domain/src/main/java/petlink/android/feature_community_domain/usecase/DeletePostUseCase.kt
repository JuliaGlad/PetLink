package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag

interface DeletePostUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    )
}
