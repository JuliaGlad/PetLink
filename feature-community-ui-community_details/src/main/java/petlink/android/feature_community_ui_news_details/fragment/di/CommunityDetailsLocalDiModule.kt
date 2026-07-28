package petlink.android.feature_community_ui_news_details.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase

@Module
class CommunityDetailsLocalDiModule {

    @CommunityDetailsScope
    @Provides
    fun provideNewsLocalDi(
        getNewsCommunityByIdUseCase: GetNewsCommunityByIdUseCase,
        subscribeToNewsCommunityUseCase: SubscribeToNewsCommunityUseCase,
        unsubscribeFromNewsCommunityUseCase: UnsubscribeFromNewsCommunityUseCase,
        updateNewsCommunityAvatarUseCase: UpdateNewsCommunityAvatarUseCase,
        updateNewsCommunityBackgroundUseCase: UpdateNewsCommunityBackgroundUseCase
    ): CommunityDetailsLocalDi = CommunityDetailsLocalDi(
        getNewsCommunityByIdUseCase= getNewsCommunityByIdUseCase,
        subscribeToNewsCommunityUseCase= subscribeToNewsCommunityUseCase,
        unsubscribeFromNewsCommunityUseCase= unsubscribeFromNewsCommunityUseCase,
        updateNewsCommunityAvatarUseCase= updateNewsCommunityAvatarUseCase,
        updateNewsCommunityBackgroundUseCase= updateNewsCommunityBackgroundUseCase
    )

}