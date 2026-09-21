package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import javax.inject.Inject

class GetCommunityPostsUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : GetCommunityPostsUseCase {
    override suspend fun invoke(
        communityId: String,
        type: CommunitiesTypeTag
    ) = router.get(type).getCommunityPosts(communityId).map { it.toDomain() }
}
