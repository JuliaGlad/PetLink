package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.model.NewsPostDomain
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import javax.inject.Inject

class CreatePostUseCaseImpl @Inject constructor(
    private val repository: NewsCommunityRepository
): CreatePostUseCase{
    override suspend fun invoke(
        communityId: String,
        postId: String,
        title: String,
        description: String,
        photos: List<String>
    ): NewsPostDomain = repository.createPost(
        communityId = communityId,
        title = title,
        description = description,
        photos = photos
    ).toDomain()
}