package petlink.android.core_ui.delegates.items.post

class PostModel(
    var postId: String,
    val title: String,
    val description: String,
    val photos: List<String>,
    val communityTitle: String = "",
    val communityAvatar: String = "",
    val communityType: String = "",
    val isQuestion: Boolean = false,
    val isOwner: Boolean = false,
    var likesCount: Int = 0,
    var likedByMe: Boolean = false,
    var commentsCount: Int = 0,
    var replyCount: Int = 0,
    var viewsCount: Int = 0,
    val onLikeClick: (PostModel) -> Unit = {},
    val onCommentClick: (PostModel) -> Unit = {},
    val onReplyClick: (PostModel) -> Unit = {},
    val onViewed: (PostModel) -> Unit = {},
    val onPhotoClick: (String) -> Unit = {}
) {
    val id: Int get() = postId.hashCode()
}
