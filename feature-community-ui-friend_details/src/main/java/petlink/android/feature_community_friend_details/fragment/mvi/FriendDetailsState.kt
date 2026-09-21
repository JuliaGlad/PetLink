package petlink.android.feature_community_friend_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_community_friend_details.fragment.model.FriendDetailsContent

data class FriendDetailsState(
    val value: LceState<FriendDetailsContent>
) : MviState
