package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class CommunityDetailsStore(
    actor: CommunityDetailsActor,
    reducer: CommunityDetailsReducer
) : MviStore<
        CommunityDetailsPartialState,
        CommunityDetailsIntent,
        CommunityDetailsState,
        CommunityDetailsEffect>(
    actor = actor,
    reducer = reducer
) {
    override fun initialStateCreator(): CommunityDetailsState = CommunityDetailsState(value = LceState.Loading)
}
