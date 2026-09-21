package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.NewsPostDomain
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
import javax.inject.Inject

class TogglePostLikeUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : TogglePostLikeUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ): NewsPostDomain = router.get(type).togglePostLike(communityId, postId).toDomain()
}
