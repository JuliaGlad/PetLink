package petlink.android.feature_community_ui_news.model

import petlink.android.feature_community_core.RoleInCommunityTag

class NewsCommunityUiModel(
    val id: String,
    val subscribers: List<String>,
    val title: String,
    val avatar: String,
    val currentUserRole: RoleInCommunityTag
)