package petlink.android.feature_community_domain.model

import petlink.android.feature_community_core.RoleInCommunityTag

class NewsCommunityFullDomainModel(
    val id: String,
    val ownerId: String,
    val subscribers: List<String>,
    val title: String,
    val description: String,
    val avatar: String,
    val role: RoleInCommunityTag,
    val background: String
)