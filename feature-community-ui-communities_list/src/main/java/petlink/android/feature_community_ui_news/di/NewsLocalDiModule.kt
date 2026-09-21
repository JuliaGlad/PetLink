package petlink.android.feature_community_ui_news.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.GetChatsUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase

@Module
class NewsLocalDiModule {

    @NewsFragmentScope
    @Provides
    fun provideNewsLocalDi(
        getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
        getNewsCommunityUseCase: GetNewsCommunityUseCase,
        getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
        getFriendsUseCase: GetFriendsUseCase,
        getOtherUsersUseCase: GetOtherUsersUseCase,
        getChatsUseCase: GetChatsUseCase
    ): NewsLocalDi = NewsLocalDi(
        getNewsCommunityUseCase = getNewsCommunityUseCase,
        getOwnedCommunitiesUseCase = getOwnedCommunitiesUseCase,
        getSubscribedCommunitiesUseCase = getSubscribedCommunitiesUseCase,
        getFriendsUseCase = getFriendsUseCase,
        getOtherUsersUseCase = getOtherUsersUseCase,
        getChatsUseCase = getChatsUseCase
    )
}
