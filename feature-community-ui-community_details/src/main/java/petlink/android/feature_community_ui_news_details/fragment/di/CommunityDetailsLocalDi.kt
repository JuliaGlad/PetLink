package petlink.android.feature_community_ui_news_details.fragment.di

import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsActor
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsReducer
import javax.inject.Inject

class CommunityDetailsLocalDi @Inject constructor(
    getNewsCommunityByIdUseCase: GetNewsCommunityByIdUseCase,
    subscribeToNewsCommunityUseCase: SubscribeToNewsCommunityUseCase,
    unsubscribeFromNewsCommunityUseCase: UnsubscribeFromNewsCommunityUseCase,
    updateNewsCommunityAvatarUseCase: UpdateNewsCommunityAvatarUseCase,
    updateNewsCommunityBackgroundUseCase: UpdateNewsCommunityBackgroundUseCase
) {

    val actor by lazy {
        CommunityDetailsActor(
            getNewsCommunityByIdUseCase = getNewsCommunityByIdUseCase,
            subscribeToNewsCommunityUseCase = subscribeToNewsCommunityUseCase,
            unsubscribeFromNewsCommunityUseCase = unsubscribeFromNewsCommunityUseCase,
            updateNewsCommunityAvatarUseCase = updateNewsCommunityAvatarUseCase,
            updateNewsCommunityBackgroundUseCase = updateNewsCommunityBackgroundUseCase
        )
    }

    val reducer by lazy { CommunityDetailsReducer() }

}