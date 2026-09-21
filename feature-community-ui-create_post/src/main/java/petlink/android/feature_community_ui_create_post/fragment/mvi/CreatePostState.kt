package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.core_mvi.MviState

data class CreatePostMviState(
    val value: CreatePostState = CreatePostState.Init
) : MviState

sealed interface CreatePostState {
    data object Init : CreatePostState
    data object Loading : CreatePostState
    class PostCreated(
        val postId: String,
        val title: String,
        val description: String,
        val photos: List<String>
    ) : CreatePostState
    class Error(val throwable: Throwable) : CreatePostState
}
