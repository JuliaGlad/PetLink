package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import javax.inject.Inject

class AddNewsCommunityUseCaseImpl @Inject constructor(
    private val router: CommunityRepositoryRouter
) : AddNewsCommunityUseCase {
    override suspend fun invoke(
        title: String,
        description: String,
        avatar: String,
        background: String,
        type: CommunitiesTypeTag
    ): String =
        router.get(type).addCommunity(
            title = title,
            description = description,
            avatar = avatar,
            background = background
        )
}
