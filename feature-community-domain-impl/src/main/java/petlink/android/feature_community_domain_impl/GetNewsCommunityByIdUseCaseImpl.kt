package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_data.mapper.toFullDomain
import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import javax.inject.Inject

class GetNewsCommunityByIdUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : GetNewsCommunityByIdUseCase {
    override suspend fun invoke(
        id: String,
        type: CommunitiesTypeTag
    ): NewsCommunityFullDomainModel =
        router.get(type).getCommunityById(id).toFullDomain()
}
