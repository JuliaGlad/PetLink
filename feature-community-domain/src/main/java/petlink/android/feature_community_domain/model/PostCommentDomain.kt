package petlink.android.feature_community_domain.model

class PostCommentDomain(
    val id: String,
    val senderId: String,
    val senderName: String,
    val senderAvatar: String,
    val text: String,
    val parentId: String = "",
    var likesCount: Int = 0,
    var likedByMe: Boolean = false,
    val photos: List<String> = emptyList()
)
