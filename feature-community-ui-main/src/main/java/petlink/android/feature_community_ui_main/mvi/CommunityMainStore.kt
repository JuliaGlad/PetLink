package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class CommunityMainStore(
    actor: CommunityMainActor,
    reducer: CommunityMainReducer
) : MviStore<
        CommunityMainPartialState,
        CommunityMainIntent,
        CommunityMainState,
        CommunityMainEffect>(
    reducer = reducer,
    actor = actor
) {
    override fun initialStateCreator(): CommunityMainState =
        CommunityMainState(value = LceState.Loading)
}