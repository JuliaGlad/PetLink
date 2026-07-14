package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CommunitiesListEffect: MviEffect {

    class NavigateToCommunityDetailsFragment(val communityId: String): CommunitiesListEffect

    data object NavigateToCreateNewsCommunityFragment: CommunitiesListEffect

    data object NavigateToCreateChatFragment: CommunitiesListEffect

    data object NavigateToCreateQuestionGroupFragment: CommunitiesListEffect

    data object NavigateToCreatePhotosCommunityFragment: CommunitiesListEffect

    data object NavigateBack: CommunitiesListEffect

}