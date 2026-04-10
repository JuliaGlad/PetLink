package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class NewsStore(
    reducer: NewsReducer,
    actor: NewsActor
): MviStore<
        NewsPartialState,
        NewsIntent,
        NewsState,
        NewsEffect>(
            reducer = reducer,
            actor = actor
        ) {
    override fun initialStateCreator(): NewsState = NewsState(value = LceState.Loading)
}