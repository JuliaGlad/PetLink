package petlink.android.feature_community_domain.model

class NewsPostDomain(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val photos: List<String>,
    val likesCount: Int = 0,
    val likedByMe: Boolean = false,
    val viewsCount: Int = 0,
    val commentsCount: Int = 0
)