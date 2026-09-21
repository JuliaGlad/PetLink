package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel

data class CommunityDetailsState(
    val value: LceState<CommunityUiModel>
) : MviState
