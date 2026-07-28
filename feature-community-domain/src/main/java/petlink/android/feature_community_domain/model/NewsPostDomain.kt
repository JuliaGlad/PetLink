package petlink.android.feature_community_domain.model

class NewsPostDomain(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val photos: List<String>
)