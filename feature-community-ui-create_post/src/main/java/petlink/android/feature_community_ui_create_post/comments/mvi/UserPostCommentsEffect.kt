package petlink.android.feature_community_ui_create_post.comments.mvi

import petlink.android.core_mvi.MviEffect

sealed interface UserPostCommentsEffect : MviEffect {
    data object CommentAdded : UserPostCommentsEffect
}
