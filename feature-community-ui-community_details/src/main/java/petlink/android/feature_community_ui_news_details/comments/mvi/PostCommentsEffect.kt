package petlink.android.feature_community_ui_news_details.comments.mvi

import petlink.android.core_mvi.MviEffect

sealed interface PostCommentsEffect : MviEffect {

    data object CommentAdded : PostCommentsEffect
}
