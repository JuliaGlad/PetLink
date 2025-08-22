package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.core_mvi.MviIntent

sealed interface ProfileIntent: MviIntent {

    data object LoadUserData: ProfileIntent

    data object LoadUserPosts: ProfileIntent

}