package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel

interface GetNewsCommunityByIdUseCase {
    suspend fun invoke(
        id: String,
        type: CommunitiesTypeTag = CommunitiesTypeTag.NewsTag
    ): NewsCommunityFullDomainModel
}
