package petlink.android.feature_community_data.dto

class NewsPostDto(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val photos: List<String>,
    val likes: List<String> = emptyList(),
    val views: Int = 0,
    val commentsCount: Int = 0,
    val likedByMe: Boolean = false
)