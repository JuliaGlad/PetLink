package petlink.android.feature_community_ui_news.di

import petlink.android.feature_community_domain.usecase.GetChatsUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_ui_news.mvi.CommunitiesListActor
import petlink.android.feature_community_ui_news.mvi.CommunitiesListReducer
import javax.inject.Inject

class NewsLocalDi @Inject constructor(
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase,
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getOtherUsersUseCase: GetOtherUsersUseCase,
    private val getChatsUseCase: GetChatsUseCase
) {

    val actor: CommunitiesListActor by lazy {
        CommunitiesListActor(
            getNewsCommunityUseCase = getNewsCommunityUseCase,
            getOwnedCommunitiesUseCase = getOwnedCommunitiesUseCase,
            getSubscribedCommunitiesUseCase = getSubscribedCommunitiesUseCase,
            getFriendsUseCase = getFriendsUseCase,
            getOtherUsersUseCase = getOtherUsersUseCase,
            getChatsUseCase = getChatsUseCase
        )
    }

    val reducer: CommunitiesListReducer by lazy { CommunitiesListReducer() }
}
