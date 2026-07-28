package petlink.android.feature_community_domain.model

import petlink.android.feature_community_core.RoleInCommunityTag

class NewsCommunityDomainModel(
    val id: String,
    val subscribers: List<String>,
    val title: String,
    val avatar: String,
    val currentUsersRole: RoleInCommunityTag
)