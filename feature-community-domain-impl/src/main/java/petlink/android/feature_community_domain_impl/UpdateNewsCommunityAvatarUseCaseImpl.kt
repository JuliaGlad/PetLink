package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import javax.inject.Inject

class UpdateNewsCommunityAvatarUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : UpdateNewsCommunityAvatarUseCase {
    override suspend fun invoke(
        communityId: String,
        newUri: String,
        type: CommunitiesTypeTag
    ) {
        router.get(type).updateCommunityAvatar(
            id = communityId,
            newUri = newUri
        )
    }
}
