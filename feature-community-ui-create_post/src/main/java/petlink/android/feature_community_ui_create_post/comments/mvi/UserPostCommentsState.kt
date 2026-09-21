package petlink.android.feature_community_ui_create_post.comments.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain

data class UserPostCommentsState(
    val value: LceState<List<UserPostCommentDomain>>,
    val commentJustAdded: Boolean = false
) : MviState
