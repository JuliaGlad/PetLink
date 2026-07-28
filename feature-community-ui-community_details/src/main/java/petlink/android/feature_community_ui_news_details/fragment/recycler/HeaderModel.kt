package petlink.android.feature_community_ui_news_details.fragment.recycler

import android.net.Uri
import petlink.android.feature_community_core.CommunitiesTypeTag
import kotlin.random.Random

class HeaderModel(
    val id: Int = Random.nextInt(),
    val title: String,
    val communityType: CommunitiesTypeTag,
    val subscribersCount: Int,
    val isOwner: Boolean,
    var background: String,
    var avatar: String,
    val aboutClickListener: () -> Unit,
    val backgroundClickListener: (() -> Unit)? = null,
    val avatarClickListener: (() -> Unit)? = null,
    val deleteClickListener: (() -> Unit)? = null
)