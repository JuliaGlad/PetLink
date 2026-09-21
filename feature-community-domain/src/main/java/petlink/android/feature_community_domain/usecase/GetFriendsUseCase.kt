package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.NewsCommunityDomainModel

interface GetFriendsUseCase {
    suspend fun invoke(): List<NewsCommunityDomainModel>
}
