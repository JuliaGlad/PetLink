package petlink.android.feature_community_ui_news_details.comments.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class PostCommentsStore(
    actor: PostCommentsActor,
    reducer: PostCommentsReducer
) : MviStore<
        PostCommentsPartialState,
        PostCommentsIntent,
        PostCommentsState,
        PostCommentsEffect>(
    actor = actor,
    reducer = reducer
) {
    override fun initialStateCreator(): PostCommentsState = PostCommentsState(value = LceState.Loading)
}
