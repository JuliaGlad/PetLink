package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.model.NewsPostDomain
import petlink.android.feature_community_domain.usecase.GetPostByIdUseCase
import javax.inject.Inject

class GetPostByIdUseCaseImpl @Inject constructor(
    private val repository: NewsCommunityRepository
) : GetPostByIdUseCase {
    override suspend fun invoke(
        postId: String,
        communityId: String
    ): NewsPostDomain = repository.getPost(
        communityId = communityId,
        postId = postId
    ).toDomain()
}