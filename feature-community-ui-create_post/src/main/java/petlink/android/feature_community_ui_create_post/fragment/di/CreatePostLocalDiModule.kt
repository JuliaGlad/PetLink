package petlink.android.feature_community_ui_create_post.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostLocalDi
import petlink.android.feature_profile_domain.usecase.user_account.CreateUserPostUseCase

@Module
class CreatePostLocalDiModule {

    @CreatePostScope
    @Provides
    fun provideCreatePostLocalDi(
        createPostUseCase: CreatePostUseCase,
        createUserPostUseCase: CreateUserPostUseCase
    ): CreatePostLocalDi = CreatePostLocalDi(
        createPostUseCase = createPostUseCase,
        createUserPostUseCase = createUserPostUseCase
    )
}
