package petlink.android.feature_community_ui_news_details.comments.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.AddPostCommentUseCase
import petlink.android.feature_community_domain.usecase.GetPostCommentsUseCase
import petlink.android.feature_community_domain.usecase.ToggleCommentLikeUseCase

@Module
class PostCommentsLocalDiModule {

    @PostCommentsScope
    @Provides
    fun providePostCommentsLocalDi(
        getPostCommentsUseCase: GetPostCommentsUseCase,
        addPostCommentUseCase: AddPostCommentUseCase,
        toggleCommentLikeUseCase: ToggleCommentLikeUseCase
    ): PostCommentsLocalDi = PostCommentsLocalDi(
        getPostCommentsUseCase = getPostCommentsUseCase,
        addPostCommentUseCase = addPostCommentUseCase,
        toggleCommentLikeUseCase = toggleCommentLikeUseCase
    )
}
