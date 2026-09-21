package petlink.android.feature_community_ui_news_details.comments.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_community_core.CommunitiesTypeTag

sealed interface PostCommentsIntent : MviIntent {

    class LoadComments(
        val communityId: String,
        val postId: String,
        val communityType: CommunitiesTypeTag
    ) : PostCommentsIntent

    class AddComment(
        val communityId: String,
        val postId: String,
        val text: String,
        val parentId: String,
        val photos: List<String> = emptyList(),
        val communityType: CommunitiesTypeTag
    ) : PostCommentsIntent

    class ToggleLike(
        val communityId: String,
        val postId: String,
        val commentId: String,
        val communityType: CommunitiesTypeTag
    ) : PostCommentsIntent
}
