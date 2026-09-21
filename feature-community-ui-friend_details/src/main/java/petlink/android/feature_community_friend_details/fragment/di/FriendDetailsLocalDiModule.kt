package petlink.android.feature_community_friend_details.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.GetUserByIdUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase

@Module
class FriendDetailsLocalDiModule {

    @FriendDetailsScope
    @Provides
    fun provideFriendDetailsLocalDi(
        getUserByIdUseCase: GetUserByIdUseCase,
        addFriendUseCase: AddFriendUseCase,
        removeFriendUseCase: RemoveFriendUseCase,
        getUserPostsUseCase: GetUserPostsUseCase,
        getUserFullDataUseCase: GetUserFullDataUseCase,
        toggleUserPostLikeUseCase: ToggleUserPostLikeUseCase,
        markUserPostViewedUseCase: MarkUserPostViewedUseCase
    ): FriendDetailsLocalDi = FriendDetailsLocalDi(
        getUserByIdUseCase = getUserByIdUseCase,
        addFriendUseCase = addFriendUseCase,
        removeFriendUseCase = removeFriendUseCase,
        getUserPostsUseCase = getUserPostsUseCase,
        getUserFullDataUseCase = getUserFullDataUseCase,
        toggleUserPostLikeUseCase = toggleUserPostLikeUseCase,
        markUserPostViewedUseCase = markUserPostViewedUseCase
    )
}
