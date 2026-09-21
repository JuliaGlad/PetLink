package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import petlink.android.feature_profile_ui_main.main_fragment.main.model.mapper.toProfileMainData

class ProfileActor(
    private val getUserDataUseCase: GetUserMainDataDomainUseCase,
    private val updateBackgroundUseCase: UpdateBackgroundUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val toggleUserPostLikeUseCase: ToggleUserPostLikeUseCase,
    private val markUserPostViewedUseCase: MarkUserPostViewedUseCase
) : MviActor<
        ProfilePartialState,
        ProfileIntent,
        ProfileState,
        ProfileEffect>() {
    override fun resolve(
        intent: ProfileIntent,
        state: ProfileState
    ): Flow<ProfilePartialState> =
        when (intent) {
            ProfileIntent.LoadUserData -> getProfileData()
            ProfileIntent.LoadUserPosts -> getUserPosts()
            is ProfileIntent.UpdateBackground -> updateBackground(intent.uri)
            is ProfileIntent.TogglePostLike -> toggleLike(intent.postId)
            is ProfileIntent.MarkPostViewed -> markViewed(intent.postId)
            is ProfileIntent.CommentAdded -> updateCommentsCount(state, intent.postId)
        }

    private fun updateBackground(uri: String) =
        flow {
            runCatching {
                updateBackgroundUseCase(uri)
            }.fold(
                onSuccess = { emit(ProfilePartialState.BackgroundUpdated) },
                onFailure = { throwable ->
                    emit(ProfilePartialState.Error(throwable))
                }
            )
        }

    private fun getProfileData() = flow {
        runCatching {
            loadUserData()
        }.fold(
            onSuccess = { data ->
                emit(ProfilePartialState.DataLoaded(data))
            },
            onFailure = { throwable ->
                emit(ProfilePartialState.Error(throwable))
            }
        )
    }

    private fun getUserPosts() = flow {
        runCatching {
            loadUserPosts()
        }.fold(
            onSuccess = { posts ->
                emit(ProfilePartialState.PostsLoaded(posts))
            },
            onFailure = { }
        )
    }

    private fun toggleLike(postId: String) = flow {
        if (postId.isBlank()) return@flow
        runCatching {
            toggleLikeUseCase(postId)
        }.fold(
            onSuccess = { post ->
                emit(
                    ProfilePartialState.PostUpdated(
                        postId = post.id,
                        likesCount = post.likesCount,
                        likedByMe = post.likedByMe
                    )
                )
            },
            onFailure = { }
        )
    }

    private fun markViewed(postId: String) = flow {
        if (postId.isBlank()) return@flow
        runCatching {
            markViewedUseCase(postId)
        }.fold(
            onSuccess = { post ->
                emit(
                    ProfilePartialState.PostUpdated(
                        postId = post.id,
                        viewsCount = post.viewsCount
                    )
                )
            },
            onFailure = { }
        )
    }

    private fun updateCommentsCount(state: ProfileState, postId: String) = flow {
        val post = state.posts.firstOrNull { it.id == postId }
        emit(
            ProfilePartialState.PostUpdated(
                postId = postId,
                commentsCount = (post?.commentsCount ?: 0) + 1
            )
        )
    }

    private suspend fun updateBackgroundUseCase(uri: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { updateBackgroundUseCase.invoke(uri) }
            ) { result -> Log.i("Result", result.toString()) }
        }.getOrThrow()

    private suspend fun loadUserData() =
        runCatchingNonCancellation {
            asyncAwait({
                getUserDataUseCase.invoke()
            }) { data ->
                data.toProfileMainData()
            }
        }.getOrThrow()

    private suspend fun loadUserPosts(): List<UserPostDomain> =
        runCatchingNonCancellation {
            asyncAwait({
                getUserPostsUseCase.invoke()
            }) { it }
        }.getOrThrow()

    private suspend fun toggleLikeUseCase(postId: String): UserPostDomain =
        runCatchingNonCancellation {
            asyncAwait({ toggleUserPostLikeUseCase.invoke("", postId) }) { it }
        }.getOrThrow()

    private suspend fun markViewedUseCase(postId: String): UserPostDomain =
        runCatchingNonCancellation {
            asyncAwait({ markUserPostViewedUseCase.invoke("", postId) }) { it }
        }.getOrThrow()
}
