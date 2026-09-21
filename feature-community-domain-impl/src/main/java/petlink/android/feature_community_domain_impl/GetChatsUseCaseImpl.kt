package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetChatsUseCase
import javax.inject.Inject

class GetChatsUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetChatsUseCase {
    override suspend fun invoke(): List<NewsCommunityDomainModel> =
        repository.getChats().map { it.toDomain() }
}
