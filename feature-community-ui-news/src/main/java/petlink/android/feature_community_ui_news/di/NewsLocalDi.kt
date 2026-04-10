package petlink.android.feature_community_ui_news.di

import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_ui_news.mvi.NewsActor
import petlink.android.feature_community_ui_news.mvi.NewsReducer
import javax.inject.Inject

class NewsLocalDi @Inject constructor(
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase,
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase
) {

    val actor: NewsActor by lazy {
        NewsActor(
            getNewsCommunityUseCase = getNewsCommunityUseCase,
            getOwnedCommunitiesUseCase = getOwnedCommunitiesUseCase,
            getSubscribedCommunitiesUseCase = getSubscribedCommunitiesUseCase
        )
    }

    val reducer: NewsReducer by lazy { NewsReducer() }

}