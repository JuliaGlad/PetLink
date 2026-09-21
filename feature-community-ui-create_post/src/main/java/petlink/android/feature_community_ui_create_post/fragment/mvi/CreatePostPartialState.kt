package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface CreatePostPartialState : MviPartialState {

    data object Loading : CreatePostPartialState

    class PostCreated(
        val postId: String,
        val title: String,
        val description: String,
        val photos: List<String>
    ) : CreatePostPartialState

    class Error(val throwable: Throwable) : CreatePostPartialState
}
