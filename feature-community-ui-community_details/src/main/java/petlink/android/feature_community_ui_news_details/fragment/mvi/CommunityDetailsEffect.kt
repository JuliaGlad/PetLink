package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CommunityDetailsEffect: MviEffect {

    class OpenDetailsBottomSheet(val title: String, val description: String, val isOwner: Boolean): CommunityDetailsEffect

    class NavigateToCreatePost(val communityId: String): CommunityDetailsEffect

    class NavigateToEditPost(val communityId: String, val postId: String): CommunityDetailsEffect

    class ShowDeleteCommunityDialog(val communityId: String): CommunityDetailsEffect

    data object UpdateAvatar: CommunityDetailsEffect

    data object UpdateBackground: CommunityDetailsEffect
}