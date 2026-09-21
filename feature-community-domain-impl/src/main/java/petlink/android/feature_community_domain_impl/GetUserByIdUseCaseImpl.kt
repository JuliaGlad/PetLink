package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toFullDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel
import petlink.android.feature_community_domain.usecase.GetUserByIdUseCase
import javax.inject.Inject

class GetUserByIdUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetUserByIdUseCase {
    override suspend fun invoke(id: String): NewsCommunityFullDomainModel =
        repository.getUserById(id).toFullDomain()
}
