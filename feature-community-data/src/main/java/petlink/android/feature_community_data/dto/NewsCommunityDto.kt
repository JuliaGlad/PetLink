package petlink.android.feature_community_data.dto

class NewsCommunityDto(
    val id: String,
    val ownerId: String,
    val subscribers: List<String>,
    val title: String,
    val description: String,
    val avatar: String,
    val role: String
)