package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.DeletePostUseCase
import javax.inject.Inject

class DeletePostUseCaseImpl @Inject constructor(
    private val repository: NewsCommunityRepository
): DeletePostUseCase {
    override suspend fun invoke(communityId: String, postId: String) {
        repository.deletePost(
            communityId = communityId,
            postId = postId
        )
    }
}