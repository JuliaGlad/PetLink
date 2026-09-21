package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.PostCommentDomain
import petlink.android.feature_community_domain.usecase.GetPostCommentsUseCase
import javax.inject.Inject

class GetPostCommentsUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : GetPostCommentsUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ): List<PostCommentDomain> =
        router.get(type).getPostComments(communityId, postId).map { it.toDomain() }
}
