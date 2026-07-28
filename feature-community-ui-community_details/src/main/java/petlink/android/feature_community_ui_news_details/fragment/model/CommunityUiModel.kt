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
    val content: MutableList<CommunitiesContent>
)

sealed interface CommunitiesContent{

    class NewsCommunityPosts(): CommunitiesContent

    class Question(): CommunitiesContent

    class Photo(): CommunitiesContent

}