package petlink.android.feature_community_ui_news_details.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.GetUsersByIdsUseCase
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
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
        updateNewsCommunityBackgroundUseCase: UpdateNewsCommunityBackgroundUseCase,
        getCommunityPostsUseCase: GetCommunityPostsUseCase,
        createPostUseCase: CreatePostUseCase,
        getUsersByIdsUseCase: GetUsersByIdsUseCase,
        togglePostLikeUseCase: TogglePostLikeUseCase,
        markPostViewedUseCase: MarkPostViewedUseCase
    ): CommunityDetailsLocalDi = CommunityDetailsLocalDi(
        getNewsCommunityByIdUseCase = getNewsCommunityByIdUseCase,
        subscribeToNewsCommunityUseCase = subscribeToNewsCommunityUseCase,
        unsubscribeFromNewsCommunityUseCase = unsubscribeFromNewsCommunityUseCase,
        updateNewsCommunityAvatarUseCase = updateNewsCommunityAvatarUseCase,
        updateNewsCommunityBackgroundUseCase = updateNewsCommunityBackgroundUseCase,
        getCommunityPostsUseCase = getCommunityPostsUseCase,
        createPostUseCase = createPostUseCase,
        getUsersByIdsUseCase = getUsersByIdsUseCase,
        togglePostLikeUseCase = togglePostLikeUseCase,
        markPostViewedUseCase = markPostViewedUseCase
    )

}
