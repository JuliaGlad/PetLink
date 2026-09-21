package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.repository.CommunityGroupsRepository
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_data.repository.PhotosCommunityRepository
import petlink.android.feature_community_data.repository.QuestionsCommunityRepository
import javax.inject.Inject

class CommunityRepositoryRouter @Inject constructor(
    private val newsCommunityRepository: NewsCommunityRepository,
    private val photosCommunityRepository: PhotosCommunityRepository,
    private val questionsCommunityRepository: QuestionsCommunityRepository
) {
    fun get(type: CommunitiesTypeTag): CommunityGroupsRepository = when (type) {
        CommunitiesTypeTag.NewsTag -> newsCommunityRepository
        CommunitiesTypeTag.PhotosTag -> photosCommunityRepository
        CommunitiesTypeTag.QuestionTag -> questionsCommunityRepository
    }
}
