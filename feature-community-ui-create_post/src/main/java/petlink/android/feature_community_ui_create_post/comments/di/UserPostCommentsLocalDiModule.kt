package petlink.android.feature_community_ui_create_post.comments.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_domain.usecase.user_account.AddUserPostCommentUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostCommentsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostCommentLikeUseCase

@Module
class UserPostCommentsLocalDiModule {

    @UserPostCommentsScope
    @Provides
    fun provideUserPostCommentsLocalDi(
        getUserPostCommentsUseCase: GetUserPostCommentsUseCase,
        addUserPostCommentUseCase: AddUserPostCommentUseCase,
        toggleUserPostCommentLikeUseCase: ToggleUserPostCommentLikeUseCase
    ): UserPostCommentsLocalDi = UserPostCommentsLocalDi(
        getUserPostCommentsUseCase = getUserPostCommentsUseCase,
        addUserPostCommentUseCase = addUserPostCommentUseCase,
        toggleUserPostCommentLikeUseCase = toggleUserPostCommentLikeUseCase
    )
}
