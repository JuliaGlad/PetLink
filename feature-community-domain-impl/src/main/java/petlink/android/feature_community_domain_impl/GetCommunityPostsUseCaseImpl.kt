package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import javax.inject.Inject

class GetCommunityPostsUseCaseImpl @Inject constructor(
    private val repository: NewsCommunityRepository
): GetCommunityPostsUseCase {
    override suspend fun invoke(communityId: String) =
        repository.getCommunityPosts(communityId).map { it.toDomain() }.toList()
}