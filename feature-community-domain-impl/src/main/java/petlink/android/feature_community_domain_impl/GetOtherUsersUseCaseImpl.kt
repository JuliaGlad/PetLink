package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import javax.inject.Inject

class GetOtherUsersUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetOtherUsersUseCase {
    override suspend fun invoke(): List<NewsCommunityDomainModel> =
        repository.getOtherUsers().map { it.toDomain() }
}
