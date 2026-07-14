package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class CommunitiesListStore(
    reducer: CommunitiesListReducer,
    actor: CommunitiesListActor
): MviStore<
        CommunitiesListPartialState,
        CommunitiesListIntent,
        CommunitiesListState,
        CommunitiesListEffect>(
            reducer = reducer,
            actor = actor
        ) {
    override fun initialStateCreator(): CommunitiesListState = CommunitiesListState(value = LceState.Loading)
}