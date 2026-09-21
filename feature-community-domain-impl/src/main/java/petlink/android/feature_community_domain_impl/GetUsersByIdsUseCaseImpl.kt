package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetUsersByIdsUseCase
import javax.inject.Inject

class GetUsersByIdsUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetUsersByIdsUseCase {
    override suspend fun invoke(ids: List<String>): List<NewsCommunityDomainModel> =
        repository.getUsersByIds(ids).map { it.toDomain() }
}
