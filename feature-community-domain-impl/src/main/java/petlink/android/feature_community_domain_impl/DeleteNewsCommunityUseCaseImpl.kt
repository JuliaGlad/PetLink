package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_domain.usecase.DeleteNewsCommunityUseCase
import javax.inject.Inject

class DeleteNewsCommunityUseCaseImpl @Inject constructor(
    val repository: NewsCommunityRepository
): DeleteNewsCommunityUseCase {
    override suspend fun invoke(id: String){
        repository.deleteNewsCommunity(id)
    }
}