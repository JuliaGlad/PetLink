package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_community_ui_news.tag.CommunitiesTypeTag

sealed interface CommunitiesListIntent: MviIntent {

    class GetCommunitiesListCommunities(val communityType: CommunitiesTypeTag): CommunitiesListIntent

}