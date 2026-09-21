package petlink.android.feature_community_friend_details.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface FriendDetailsEffect : MviEffect {

    data object NavigateBack : FriendDetailsEffect
}
