package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_domain.model.NewsPostDomain
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import javax.inject.Inject

class CreatePostUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : CreatePostUseCase {
    override suspend fun invoke(
        communityId: String,
        postId: String,
        title: String,
        description: String,
        photos: List<String>,
        type: CommunitiesTypeTag
    ): NewsPostDomain = router.get(type).createPost(
        communityId = communityId,
        title = title,
        description = description,
        photos = photos
    ).toDomain()
}
