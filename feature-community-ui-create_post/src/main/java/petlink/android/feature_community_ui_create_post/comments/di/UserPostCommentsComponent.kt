package petlink.android.feature_community_ui_create_post.comments.di

import dagger.BindsInstance
import dagger.Component
import petlink.android.feature_community_ui_create_post.comments.UserPostCommentsBottomSheet
import petlink.android.feature_profile_domain.usecase.user_account.AddUserPostCommentUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostCommentsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostCommentLikeUseCase
import javax.inject.Scope

@UserPostCommentsScope
@Component(modules = [UserPostCommentsLocalDiModule::class])
interface UserPostCommentsComponent {

    fun inject(bottomSheet: UserPostCommentsBottomSheet)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance getUserPostCommentsUseCase: GetUserPostCommentsUseCase,
            @BindsInstance addUserPostCommentUseCase: AddUserPostCommentUseCase,
            @BindsInstance toggleUserPostCommentLikeUseCase: ToggleUserPostCommentLikeUseCase
        ): UserPostCommentsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPostCommentsScope
