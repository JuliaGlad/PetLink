package petlink.android.feature_community_ui_create_post.comments.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class UserPostCommentsStore(
    actor: UserPostCommentsActor,
    reducer: UserPostCommentsReducer
) : MviStore<
        UserPostCommentsPartialState,
        UserPostCommentsIntent,
        UserPostCommentsState,
        UserPostCommentsEffect>(
    actor = actor,
    reducer = reducer
) {
    override fun initialStateCreator(): UserPostCommentsState =
        UserPostCommentsState(value = LceState.Loading)
}
