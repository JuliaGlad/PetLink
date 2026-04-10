package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel

data class CommunityMainState(val value: LceState<DiffCommunitiesModel>): MviState