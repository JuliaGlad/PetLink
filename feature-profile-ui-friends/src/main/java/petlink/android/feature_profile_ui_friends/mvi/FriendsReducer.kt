package petlink.android.feature_profile_ui_friends.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer

class FriendsReducer : MviReducer<FriendsPartialState, FriendsState> {
    override fun reduce(
        prevState: FriendsState,
        partialState: FriendsPartialState
    ): FriendsState =
        when (partialState) {
            FriendsPartialState.Loading -> prevState.copy(value = LceState.Loading)
            is FriendsPartialState.DataLoaded -> prevState.copy(
                value = LceState.Content(partialState.content)
            )
            is FriendsPartialState.QueryChanged -> prevState.copy(query = partialState.query)
            is FriendsPartialState.Error -> prevState.copy(
                value = LceState.Error(partialState.throwable)
            )
        }
}
