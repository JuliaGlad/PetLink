package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.PostCommentDomain
import petlink.android.feature_community_domain.usecase.AddPostCommentUseCase
import javax.inject.Inject

class AddPostCommentUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : AddPostCommentUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>,
        type: CommunitiesTypeTag
    ): PostCommentDomain =
        router.get(type).addPostComment(communityId, postId, text, parentId, photos).toDomain()
}
