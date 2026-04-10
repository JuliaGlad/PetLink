package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CommunityMainIntent: MviIntent {

    data object GetCommunitiesData: CommunityMainIntent

}