package petlink.android.feature_profile_domain.model.user_account

class UserPostDomain(
    val id: String,
    val title: String,
    val description: String,
    val photos: List<String>,
    var likesCount: Int = 0,
    var likedByMe: Boolean = false,
    var commentsCount: Int = 0,
    var viewsCount: Int = 0,
    val createdAt: Long = 0
)
