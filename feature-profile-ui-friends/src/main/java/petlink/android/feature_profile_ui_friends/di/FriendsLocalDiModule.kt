package petlink.android.feature_profile_ui_friends.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase

@Module
class FriendsLocalDiModule {

    @FriendsScope
    @Provides
    fun provideFriendsLocalDi(
        getFriendsUseCase: GetFriendsUseCase,
        getOtherUsersUseCase: GetOtherUsersUseCase,
        addFriendUseCase: AddFriendUseCase,
        removeFriendUseCase: RemoveFriendUseCase
    ): FriendsLocalDi = FriendsLocalDi(
        getFriendsUseCase = getFriendsUseCase,
        getOtherUsersUseCase = getOtherUsersUseCase,
        addFriendUseCase = addFriendUseCase,
        removeFriendUseCase = removeFriendUseCase
    )
}
