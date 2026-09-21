package petlink.android.feature_community_ui_create_post.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_profile_domain.usecase.user_account.CreateUserPostUseCase

class CreatePostActor(
    private val createPostUseCase: CreatePostUseCase,
    private val createUserPostUseCase: CreateUserPostUseCase
) : MviActor<
        CreatePostPartialState,
        CreatePostIntent,
        CreatePostMviState,
        CreatePostEffect>() {
    override fun resolve(
        intent: CreatePostIntent,
        state: CreatePostMviState
    ): Flow<CreatePostPartialState> =
        when (intent) {
            is CreatePostIntent.CreatePost -> createPost(
                title = intent.title,
                description = intent.description,
                photos = intent.photos,
                isUserPost = intent.isUserPost,
                communityId = intent.communityId,
                communityType = intent.communityType
            )
        }

    private fun createPost(
        title: String,
        description: String,
        photos: List<String>,
        isUserPost: Boolean,
        communityId: String,
        communityType: CommunitiesTypeTag
    ) = flow {
        emit(CreatePostPartialState.Loading)
        runCatching {
            if (isUserPost) {
                createUserPost(title, description, photos)
            } else {
                createCommunityPost(communityId, title, description, photos, communityType)
            }
        }.fold(
            onSuccess = { emit(it) },
            onFailure = { emit(CreatePostPartialState.Error(it)) }
        )
    }

    private suspend fun createUserPost(
        title: String,
        description: String,
        photos: List<String>
    ): CreatePostPartialState.PostCreated =
        runCatchingNonCancellation {
            asyncAwait({
                createUserPostUseCase.invoke(title, description, photos)
            }) { post ->
                CreatePostPartialState.PostCreated(
                    postId = post.id,
                    title = post.title,
                    description = post.description,
                    photos = post.photos
                )
            }
        }.getOrThrow()

    private suspend fun createCommunityPost(
        communityId: String,
        title: String,
        description: String,
        photos: List<String>,
        communityType: CommunitiesTypeTag
    ): CreatePostPartialState.PostCreated =
        runCatchingNonCancellation {
            asyncAwait({
                createPostUseCase.invoke(
                    communityId = communityId,
                    postId = "",
                    title = title,
                    description = description,
                    photos = photos,
                    type = communityType
                )
            }) { post ->
                CreatePostPartialState.PostCreated(
                    postId = post.id,
                    title = post.title,
                    description = post.description,
                    photos = post.photos
                )
            }
        }.getOrThrow()
}
