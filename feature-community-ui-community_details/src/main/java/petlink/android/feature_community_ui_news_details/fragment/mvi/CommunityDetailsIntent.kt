package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_community_core.CommunitiesTypeTag

sealed interface CommunityDetailsIntent : MviIntent {

    class GetCommunityDetails(val communityTypeTag: CommunitiesTypeTag, val id: String) :
        CommunityDetailsIntent

    class UpdateAvatar(
        val communityTypeTag: CommunitiesTypeTag,
        val id: String,
        val uri: String
    ) : CommunityDetailsIntent

    class UpdateBackground(
        val communityTypeTag: CommunitiesTypeTag,
        val id: String,
        val uri: String
    ) : CommunityDetailsIntent

    class Subscribe(val communityTypeTag: CommunitiesTypeTag, val id: String) :
        CommunityDetailsIntent

    class Unsubscribe(val communityTypeTag: CommunitiesTypeTag, val id: String) :
        CommunityDetailsIntent
}