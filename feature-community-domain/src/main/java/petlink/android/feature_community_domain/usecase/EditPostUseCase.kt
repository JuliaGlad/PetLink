package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag

interface EditPostUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String,
        newTitle: String,
        newDescription: String,
        newPhotos: List<String>,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    )
}
