package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import javax.inject.Inject

class GetFriendsUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetFriendsUseCase {
    override suspend fun invoke(): List<NewsCommunityDomainModel> =
        repository.getFriends().map { it.toDomain() }
}
