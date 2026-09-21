package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.DeletePostUseCase
import javax.inject.Inject

class DeletePostUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : DeletePostUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) {
        router.get(type).deletePost(
            communityId = communityId,
            postId = postId
        )
    }
}
