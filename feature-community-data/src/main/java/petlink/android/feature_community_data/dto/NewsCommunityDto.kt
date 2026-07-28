package petlink.android.feature_community_data.dto

import petlink.android.feature_community_core.RoleInCommunityTag

class NewsCommunityDto(
    val id: String,
    val ownerId: String,
    val subscribers: List<String>,
    val title: String,
    val description: String,
    val avatar: String,
    val role: RoleInCommunityTag,
    val background: String
)