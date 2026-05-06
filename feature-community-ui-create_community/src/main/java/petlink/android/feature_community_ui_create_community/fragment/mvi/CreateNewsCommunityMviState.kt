package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.core_mvi.MviState

data class CreateNewsCommunityMviState(val value: CreateNewsCommunityState): MviState

sealed interface CreateNewsCommunityState{
    data object Init: CreateNewsCommunityState
    data object Loading: CreateNewsCommunityState
    class CommunityCreated(val communityId: String): CreateNewsCommunityState
    class Error(val throwable: Throwable): CreateNewsCommunityState
}