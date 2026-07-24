package petlink.android.feature_community_ui_news_details.fragment.recycler

import kotlin.random.Random

class HeaderModel(
    val id: Int = Random.nextInt(),
    val title: String,
    val communityType: String,
    val subscribersCount: Int,
    val isOwner: Boolean,
    val clickListener: () -> Unit,
    val backgroundClickListener: () -> Unit,
    val avatarClickListener: () -> Unit,
    val deleteClickListener: (() -> Unit)? = null
)