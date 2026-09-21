package petlink.android.feature_community_friend_details.fragment.di

import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.GetUserByIdUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsActor
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsReducer
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase
import javax.inject.Inject

class FriendDetailsLocalDi @Inject constructor(
    getUserByIdUseCase: GetUserByIdUseCase,
    addFriendUseCase: AddFriendUseCase,
    removeFriendUseCase: RemoveFriendUseCase,
    getUserPostsUseCase: GetUserPostsUseCase,
    getUserFullDataUseCase: GetUserFullDataUseCase,
    toggleUserPostLikeUseCase: ToggleUserPostLikeUseCase,
    markUserPostViewedUseCase: MarkUserPostViewedUseCase
) {

    val actor by lazy {
        FriendDetailsActor(
            getUserByIdUseCase = getUserByIdUseCase,
            addFriendUseCase = addFriendUseCase,
            removeFriendUseCase = removeFriendUseCase,
            getUserPostsUseCase = getUserPostsUseCase,
            getUserFullDataUseCase = getUserFullDataUseCase,
            toggleUserPostLikeUseCase = toggleUserPostLikeUseCase,
            markUserPostViewedUseCase = markUserPostViewedUseCase
        )
    }

    val reducer by lazy { FriendDetailsReducer() }
}
