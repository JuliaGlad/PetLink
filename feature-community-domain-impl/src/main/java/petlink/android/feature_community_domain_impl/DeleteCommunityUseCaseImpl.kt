package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.DeleteCommunityUseCase
import javax.inject.Inject

class DeleteCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): DeleteCommunityUseCase {
    override suspend fun invoke(id: String){
        repository.deleteNewsCommunity(id)
    }
}