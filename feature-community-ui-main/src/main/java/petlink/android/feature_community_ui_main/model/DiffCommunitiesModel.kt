package petlink.android.feature_community_ui_main.model

import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_core.RoleInCommunityTag

class FeedPostModel(
    val postId: String,
    val role: RoleInCommunityTag,
    val communityId: String,
    val communityType: CommunitiesTypeTag,
    val communityTitle: String,
    val communityAvatar: String,
    val title: String,
    val description: String,
    val photos: List<String>,
    val likesCount: Int,
    val likedByMe: Boolean,
    val commentsCount: Int,
    val viewsCount: Int
)

class DiffCommunitiesModel(
    val feed: List<FeedPostModel> = emptyList()
)
