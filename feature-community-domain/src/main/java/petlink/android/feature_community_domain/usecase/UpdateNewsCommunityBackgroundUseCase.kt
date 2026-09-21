package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag

interface UpdateNewsCommunityBackgroundUseCase {
    suspend fun invoke(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    )
}
