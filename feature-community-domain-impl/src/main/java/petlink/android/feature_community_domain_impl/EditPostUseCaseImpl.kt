package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.EditPostUseCase
import javax.inject.Inject

class EditPostUseCaseImpl @Inject constructor(
    private val repository: NewsCommunityRepository
): EditPostUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        newTitle: String,
        newDescription: String,
        newPhotos: List<String>
    ) {
        repository.editPost(
            communityId = communityId,
            postId = postId,
            title = newTitle,
            description = newDescription,
            photos = newPhotos
        )
    }
}