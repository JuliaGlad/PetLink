package petlink.android.feature_profile_data.dto

class UserPostCommentDto(
    val id: String,
    val senderId: String,
    val senderName: String,
    val senderAvatar: String,
    val text: String,
    val parentId: String = "",
    val likesCount: Int = 0,
    val likedByMe: Boolean = false,
    val photos: List<String> = emptyList()
)
