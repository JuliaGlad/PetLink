package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import javax.inject.Inject

class UpdateNewsCommunityBackgroundUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : UpdateNewsCommunityBackgroundUseCase {
    override suspend fun invoke(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag
    ) {
        router.get(type).updateCommunityBackground(
            id = communityId,
            newUri = newUri
        )
    }
}
