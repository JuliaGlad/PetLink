package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CommunityMainEffect: MviEffect {

    data object OpenNewsFragment: CommunityMainEffect

    data object OpenQuestionFragment: CommunityMainEffect

    data object OpenPhotosFragment: CommunityMainEffect

    data object OpenFriendsFragment: CommunityMainEffect

    data object OpenChatsFragment: CommunityMainEffect

}