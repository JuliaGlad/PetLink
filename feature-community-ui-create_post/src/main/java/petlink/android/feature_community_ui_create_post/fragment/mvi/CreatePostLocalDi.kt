package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_profile_domain.usecase.user_account.CreateUserPostUseCase
import javax.inject.Inject

class CreatePostLocalDi @Inject constructor(
    createPostUseCase: CreatePostUseCase,
    createUserPostUseCase: CreateUserPostUseCase
) {
    val actor by lazy {
        CreatePostActor(
            createPostUseCase = createPostUseCase,
            createUserPostUseCase = createUserPostUseCase
        )
    }

    val reducer by lazy { CreatePostReducer() }
}
