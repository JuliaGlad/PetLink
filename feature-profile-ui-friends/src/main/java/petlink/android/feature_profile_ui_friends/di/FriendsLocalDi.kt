package petlink.android.feature_profile_ui_friends.di

import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import petlink.android.feature_profile_ui_friends.mvi.FriendsActor
import petlink.android.feature_profile_ui_friends.mvi.FriendsReducer
import javax.inject.Inject

class FriendsLocalDi @Inject constructor(
    getFriendsUseCase: GetFriendsUseCase,
    getOtherUsersUseCase: GetOtherUsersUseCase,
    addFriendUseCase: AddFriendUseCase,
    removeFriendUseCase: RemoveFriendUseCase
) {
    val actor by lazy {
        FriendsActor(
            getFriendsUseCase = getFriendsUseCase,
            getOtherUsersUseCase = getOtherUsersUseCase,
            addFriendUseCase = addFriendUseCase,
            removeFriendUseCase = removeFriendUseCase
        )
    }

    val reducer by lazy { FriendsReducer() }
}
