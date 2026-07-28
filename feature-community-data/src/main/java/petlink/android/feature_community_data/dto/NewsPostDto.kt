package petlink.android.feature_community_data.dto

class NewsPostDto(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val photos: List<String>
)