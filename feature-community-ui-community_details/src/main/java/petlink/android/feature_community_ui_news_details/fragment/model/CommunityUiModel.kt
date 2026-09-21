package petlink.android.feature_community_ui_news_details.fragment.model

import petlink.android.feature_community_core.RoleInCommunityTag

class CommunityUiModel(
    val communityId: String,
    var title: String,
    var description: String,
    var subscribersCount: Int,
    var role: RoleInCommunityTag,
    var avatar: String,
    var background: String,
    val subscriberIds: List<String> = emptyList(),
    var subscribers: List<SubscriberUi> = emptyList(),
    val content: MutableList<CommunitiesContent> = mutableListOf()
)

class SubscriberUi(
    val id: String,
    val name: String,
    val avatar: String
)

sealed interface CommunitiesContent {

    class Post(
        val id: String,
        val title: String,
        val description: String,
        val photos: List<String>,
        var likesCount: Int = 0,
        var likedByMe: Boolean = false,
        var commentsCount: Int = 0,
        var viewsCount: Int = 0
    ) : CommunitiesContent
}
