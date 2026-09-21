package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag

interface DeleteNewsCommunityUseCase {
    suspend fun invoke(
        id: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    )
}
