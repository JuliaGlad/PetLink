package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviEffect
import petlink.android.feature_community_core.RoleInCommunityTag

sealed interface CommunitiesListEffect: MviEffect {

    class NavigateToCommunityDetailsFragment(val communityId: String, val role: RoleInCommunityTag): CommunitiesListEffect

    data object NavigateToCreateNewsCommunityFragment: CommunitiesListEffect

    data object NavigateToCreateChatFragment: CommunitiesListEffect

    data object NavigateToCreateQuestionGroupFragment: CommunitiesListEffect

    data object NavigateToCreatePhotosCommunityFragment: CommunitiesListEffect

    data object NavigateBack: CommunitiesListEffect

}