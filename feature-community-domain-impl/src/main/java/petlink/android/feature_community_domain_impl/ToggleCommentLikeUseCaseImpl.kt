package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.PostCommentDomain
import petlink.android.feature_community_domain.usecase.ToggleCommentLikeUseCase
import javax.inject.Inject

class ToggleCommentLikeUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : ToggleCommentLikeUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        commentId: String,
        type: CommunitiesTypeTag
    ): PostCommentDomain =
        router.get(type).toggleCommentLike(communityId, postId, commentId).toDomain()
}
