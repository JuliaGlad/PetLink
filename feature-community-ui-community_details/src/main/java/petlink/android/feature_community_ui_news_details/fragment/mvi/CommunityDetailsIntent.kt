package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CommunityDetailsIntent: MviIntent {

    class GetCommunityDetails(val id: String): CommunityDetailsIntent

    class UpdateAvatar(val id: String, val uri: String): CommunityDetailsIntent

    class UpdateBackground(val id: String, val uri: String): CommunityDetailsIntent

    class Subscribe(val id: String): CommunityDetailsIntent

    class Unsubscribe(val id: String): CommunityDetailsIntent
}