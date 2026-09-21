package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.core_mvi.MviStore

class CreatePostStore(
    reducer: CreatePostReducer,
    actor: CreatePostActor
) : MviStore<
        CreatePostPartialState,
        CreatePostIntent,
        CreatePostMviState,
        CreatePostEffect>(
    reducer = reducer,
    actor = actor
) {
    override fun initialStateCreator(): CreatePostMviState = CreatePostMviState()
}
