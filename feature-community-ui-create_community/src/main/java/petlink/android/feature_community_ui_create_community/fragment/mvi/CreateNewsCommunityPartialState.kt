package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.core_mvi.MviPartialState

sealed interface CreateNewsCommunityPartialState: MviPartialState {

    data object Loading: CreateNewsCommunityPartialState

    class CommunityCreated(val communityId: String): CreateNewsCommunityPartialState

    class Error(val throwable: Throwable): CreateNewsCommunityPartialState
}