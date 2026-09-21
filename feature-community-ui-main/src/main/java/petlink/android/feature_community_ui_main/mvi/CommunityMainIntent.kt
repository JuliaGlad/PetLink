package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_community_core.CommunitiesTypeTag

sealed interface CommunityMainIntent : MviIntent {

    data object GetCommunitiesData : CommunityMainIntent

    class TogglePostLike(
        val communityId: String,
        val postId: String,
        val communityType: CommunitiesTypeTag
    ) : CommunityMainIntent

    class MarkPostViewed(
        val communityId: String,
        val postId: String,
        val communityType: CommunitiesTypeTag
    ) : CommunityMainIntent
}
