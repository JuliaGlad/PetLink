package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag

interface UpdateNewsCommunityUseCase {
    suspend fun invoke(
        id: String,
        newTitle: String?,
        newDescription: String?,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    )
}
