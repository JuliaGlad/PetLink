package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag

interface AddNewsCommunityUseCase {
    suspend fun invoke(
        title: String,
        description: String,
        avatar: String,
        background: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    ): String
}