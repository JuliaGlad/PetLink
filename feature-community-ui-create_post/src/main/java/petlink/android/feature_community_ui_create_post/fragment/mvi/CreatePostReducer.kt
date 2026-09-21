package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.core_mvi.MviReducer

class CreatePostReducer : MviReducer<CreatePostPartialState, CreatePostMviState> {
    override fun reduce(
        prevState: CreatePostMviState,
        partialState: CreatePostPartialState
    ): CreatePostMviState =
        when (partialState) {
            CreatePostPartialState.Loading -> prevState.copy(value = CreatePostState.Loading)
            is CreatePostPartialState.PostCreated -> prevState.copy(
                value = CreatePostState.PostCreated(
                    postId = partialState.postId,
                    title = partialState.title,
                    description = partialState.description,
                    photos = partialState.photos
                )
            )
            is CreatePostPartialState.Error -> prevState.copy(
                value = CreatePostState.Error(partialState.throwable)
            )
        }
}
