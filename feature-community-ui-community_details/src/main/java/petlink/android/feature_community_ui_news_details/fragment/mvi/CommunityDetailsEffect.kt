package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CommunityDetailsEffect: MviEffect {

    class OpenUpdateDetailsBottomSheet(val communityId: String): CommunityDetailsEffect

    class NavigateToCreatePost(val communityId: String): CommunityDetailsEffect

    class NavigateToEditPost(val communityId: String, val postId: String): CommunityDetailsEffect

    class ShowDeleteCommunityDialog(val communityId: String): CommunityDetailsEffect
}