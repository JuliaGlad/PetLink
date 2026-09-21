package petlink.android.feature_community_ui_news_details.comments.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_community_domain.model.PostCommentDomain

data class PostCommentsState(
    val value: LceState<List<PostCommentDomain>>,
    val commentJustAdded: Boolean = false
) : MviState
