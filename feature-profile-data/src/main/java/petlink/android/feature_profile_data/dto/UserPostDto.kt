package petlink.android.feature_profile_data.dto

class UserPostDto(
    val id: String,
    val title: String,
    val description: String,
    val photos: List<String>,
    val likesCount: Int = 0,
    val likedByMe: Boolean = false,
    val commentsCount: Int = 0,
    val viewsCount: Int = 0,
    val createdAt: Long = 0
)
