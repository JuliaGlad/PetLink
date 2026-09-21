package petlink.android.feature_community_friend_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class FriendDetailsStore(
    actor: FriendDetailsActor,
    reducer: FriendDetailsReducer
) : MviStore<
        FriendDetailsPartialState,
        FriendDetailsIntent,
        FriendDetailsState,
        FriendDetailsEffect>(
    reducer = reducer,
    actor = actor
) {
    override fun initialStateCreator(): FriendDetailsState =
        FriendDetailsState(value = LceState.Loading)
}
