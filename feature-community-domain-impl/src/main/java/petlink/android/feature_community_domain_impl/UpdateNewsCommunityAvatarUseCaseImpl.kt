package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import javax.inject.Inject

class UpdateNewsCommunityAvatarUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
) : UpdateNewsCommunityAvatarUseCase {
    override suspend fun invoke(
        communityId: String,
        newUri: String
    ) {
        repository.updateNewsCommunityAvatar(
            id = communityId,
            newUri = newUri
        )
    }
}