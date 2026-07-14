package petlink.android.feature_community_ui_news.di

import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_ui_news.mvi.CommunitiesListActor
import petlink.android.feature_community_ui_news.mvi.CommunitiesListReducer
import javax.inject.Inject

class NewsLocalDi @Inject constructor(
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase,
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase
) {

    val actor: CommunitiesListActor by lazy {
        CommunitiesListActor(
            getNewsCommunityUseCase = getNewsCommunityUseCase,
            getOwnedCommunitiesUseCase = getOwnedCommunitiesUseCase,
            getSubscribedCommunitiesUseCase = getSubscribedCommunitiesUseCase
        )
    }

    val reducer: CommunitiesListReducer by lazy { CommunitiesListReducer() }

}