package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_ui_main.main_fragment.main.model.ProfileMainDataUi

class ProfileReducer : MviReducer<
        ProfilePartialState,
        ProfileState> {
    override fun reduce(
        prevState: ProfileState,
        partialState: ProfilePartialState
    ): ProfileState =
        when (partialState) {
            is ProfilePartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is ProfilePartialState.Error -> updateError(prevState, partialState.throwable)
            ProfilePartialState.Loading -> updateLoading(prevState)
            is ProfilePartialState.PostsLoaded -> updatePostsLoaded(prevState, partialState.posts)
            is ProfilePartialState.PostUpdated -> updatePost(prevState, partialState)
            ProfilePartialState.BackgroundUpdated -> updateBackgroundUpdated(prevState)
        }

    private fun updateBackgroundUpdated(prevState: ProfileState) = prevState.copy()

    private fun updateDataLoaded(prevState: ProfileState, data: ProfileMainDataUi) =
        prevState.copy(value = LceState.Content(data))

    private fun updatePostsLoaded(prevState: ProfileState, posts: List<UserPostDomain>) =
        prevState.copy(posts = posts)

    private fun updatePost(
        prevState: ProfileState,
        partialState: ProfilePartialState.PostUpdated
    ): ProfileState {
        prevState.posts.firstOrNull { it.id == partialState.postId }?.let { post ->
            partialState.likesCount?.let { post.likesCount = it }
            partialState.likedByMe?.let { post.likedByMe = it }
            partialState.viewsCount?.let { post.viewsCount = it }
            partialState.commentsCount?.let { post.commentsCount = it }
        }
        return prevState.copy(posts = prevState.posts.toList())
    }

    private fun updateError(prevState: ProfileState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable))

    private fun updateLoading(prevState: ProfileState) =
        prevState.copy(value = LceState.Loading)
}
