package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import petlink.android.core_mvi.MviIntent

sealed interface ProfileIntent : MviIntent {

    data object LoadUserData : ProfileIntent

    data object LoadUserPosts : ProfileIntent

    class UpdateBackground(val uri: String) : ProfileIntent

    class TogglePostLike(val postId: String) : ProfileIntent

    class MarkPostViewed(val postId: String) : ProfileIntent

    class CommentAdded(val postId: String) : ProfileIntent
}
