package petlink.android.feature_profile_ui_friends.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class FriendsStore(
    actor: FriendsActor,
    reducer: FriendsReducer
) : MviStore<
        FriendsPartialState,
        FriendsIntent,
        FriendsState,
        FriendsEffect>(
    reducer = reducer,
    actor = actor
) {
    override fun initialStateCreator(): FriendsState =
        FriendsState(value = LceState.Loading)
}
