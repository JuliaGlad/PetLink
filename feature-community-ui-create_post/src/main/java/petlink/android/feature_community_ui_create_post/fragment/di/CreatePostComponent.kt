package petlink.android.feature_community_ui_create_post.fragment.di

import dagger.BindsInstance
import dagger.Component
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_community_ui_create_post.fragment.CreatePostFragment
import petlink.android.feature_profile_domain.usecase.user_account.CreateUserPostUseCase
import javax.inject.Scope

@CreatePostScope
@Component(modules = [CreatePostLocalDiModule::class])
interface CreatePostComponent {

    fun inject(fragment: CreatePostFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance createPostUseCase: CreatePostUseCase,
            @BindsInstance createUserPostUseCase: CreateUserPostUseCase
        ): CreatePostComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreatePostScope
