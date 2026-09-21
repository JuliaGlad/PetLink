package petlink.android.feature_community_ui_news_details.fragment.recycler

data class PhotoGridItem(
    val uri: String,
    val postId: String
)

class PhotoGridModel(
    val id: Int,
    val photos: MutableList<PhotoGridItem>,
    val onPhotoClick: (PhotoGridItem) -> Unit = {}
)
