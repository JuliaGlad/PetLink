package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_community_core.CommunitiesTypeTag

sealed interface CreatePostIntent : MviIntent {

    class CreatePost(
        val title: String,
        val description: String,
        val photos: List<String>,
        val isUserPost: Boolean,
        val communityId: String,
        val communityType: CommunitiesTypeTag
    ) : CreatePostIntent
}
