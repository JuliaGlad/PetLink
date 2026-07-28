package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_community_core.AllSocialTypeTag

sealed interface CommunitiesListIntent: MviIntent {

    class GetCommunitiesListCommunities(val communityType: AllSocialTypeTag): CommunitiesListIntent

}