package petlink.android.feature_community_ui_create_post.comments.mvi

import petlink.android.core_mvi.MviIntent

sealed interface UserPostCommentsIntent : MviIntent {

    class LoadComments(
        val userId: String,
        val postId: String
    ) : UserPostCommentsIntent

    class AddComment(
        val userId: String,
        val postId: String,
        val text: String,
        val parentId: String,
        val photos: List<String> = emptyList()
    ) : UserPostCommentsIntent

    class ToggleLike(
        val userId: String,
        val postId: String,
        val commentId: String
    ) : UserPostCommentsIntent
}
