package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviEffect

sealed interface NewsEffect: MviEffect {

    class NavigateToNewsCommunityDetailsFragment(val communityId: String): NewsEffect

    data object NavigateToCreateCommunityFragment: NewsEffect

    data object NavigateBack: NewsEffect

}