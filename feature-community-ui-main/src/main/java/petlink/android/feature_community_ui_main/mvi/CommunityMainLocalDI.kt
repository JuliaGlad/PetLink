package petlink.android.feature_community_ui_main.mvi

import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
import javax.inject.Inject

class CommunityMainLocalDI @Inject constructor(
    getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
    getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    getNewsCommunityUseCase: GetNewsCommunityUseCase,
    getCommunityPostsUseCase: GetCommunityPostsUseCase,
    togglePostLikeUseCase: TogglePostLikeUseCase,
    markPostViewedUseCase: MarkPostViewedUseCase
) {

    val actor by lazy {
        CommunityMainActor(
            getSubscribedCommunitiesUseCase = getSubscribedCommunitiesUseCase,
            getOwnedCommunitiesUseCase = getOwnedCommunitiesUseCase,
            getNewsCommunityUseCase = getNewsCommunityUseCase,
            getCommunityPostsUseCase = getCommunityPostsUseCase,
            togglePostLikeUseCase = togglePostLikeUseCase,
            markPostViewedUseCase = markPostViewedUseCase
        )
    }

    val reducer by lazy { CommunityMainReducer() }
}
