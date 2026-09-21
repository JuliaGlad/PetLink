package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import javax.inject.Inject

class UpdateNewsCommunityUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : UpdateNewsCommunityUseCase {
    override suspend fun invoke(
        id: String,
        newTitle: String?,
        newDescription: String?,
        type: CommunitiesTypeTag
    ) {
        router.get(type).updateCommunityData(
            id = id,
            newTitle = newTitle,
            newDescription = newDescription
        )
    }
}
