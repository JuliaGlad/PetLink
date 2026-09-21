package petlink.android.feature_community_ui_main.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
import petlink.android.feature_community_ui_main.mvi.CommunityMainLocalDI

@Module
class CommunityMainDiModule {

    @CommunityMainScope
    @Provides
    fun provideCommunityMainLocalDi(
        getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
        getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
        getNewsCommunityUseCase: GetNewsCommunityUseCase,
        getCommunityPostsUseCase: GetCommunityPostsUseCase,
        togglePostLikeUseCase: TogglePostLikeUseCase,
        markPostViewedUseCase: MarkPostViewedUseCase
    ): CommunityMainLocalDI = CommunityMainLocalDI(
        getSubscribedCommunitiesUseCase = getSubscribedCommunitiesUseCase,
        getOwnedCommunitiesUseCase = getOwnedCommunitiesUseCase,
        getNewsCommunityUseCase = getNewsCommunityUseCase,
        getCommunityPostsUseCase = getCommunityPostsUseCase,
        togglePostLikeUseCase = togglePostLikeUseCase,
        markPostViewedUseCase = markPostViewedUseCase
    )
}
