package petlink.android.feature_community_ui_news_details.fragment.recycler

import petlink.android.feature_community_core.CommunitiesTypeTag
import kotlin.random.Random

class HeaderModel(
    val id: Int = Random.nextInt(),
    var title: String,
    val communityType: CommunitiesTypeTag,
    var subscribersCount: Int,
    val isOwner: Boolean,
    var background: String,
    var avatar: String,
    val aboutClickListener: () -> Unit,
    val backgroundClickListener: (() -> Unit)? = null,
    val avatarClickListener: (() -> Unit)? = null,
    val deleteClickListener: (() -> Unit)? = null
)