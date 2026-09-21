package petlink.android.feature_profile_ui_friends.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_profile_ui_friends.model.FriendsContent

data class FriendsState(
    val value: LceState<FriendsContent>,
    val query: String = ""
) : MviState
