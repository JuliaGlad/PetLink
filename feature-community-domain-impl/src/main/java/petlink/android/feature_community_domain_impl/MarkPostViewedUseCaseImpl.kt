package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.NewsPostDomain
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import javax.inject.Inject

class MarkPostViewedUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : MarkPostViewedUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ): NewsPostDomain = router.get(type).markPostViewed(communityId, postId).toDomain()
}
