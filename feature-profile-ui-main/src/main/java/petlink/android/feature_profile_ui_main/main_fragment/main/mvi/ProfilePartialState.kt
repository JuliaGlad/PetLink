package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_ui_main.main_fragment.main.model.ProfileMainDataUi

sealed interface ProfilePartialState : MviPartialState {

    data object Loading : ProfilePartialState

    class Error(val throwable: Throwable) : ProfilePartialState

    class DataLoaded(val data: ProfileMainDataUi) : ProfilePartialState

    class PostsLoaded(val posts: List<UserPostDomain>) : ProfilePartialState

    class PostUpdated(
        val postId: String,
        val likesCount: Int? = null,
        val likedByMe: Boolean? = null,
        val viewsCount: Int? = null,
        val commentsCount: Int? = null
    ) : ProfilePartialState

    data object BackgroundUpdated : ProfilePartialState
}
