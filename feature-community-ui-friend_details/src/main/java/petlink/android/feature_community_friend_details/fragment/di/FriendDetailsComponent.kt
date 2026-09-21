package petlink.android.feature_community_friend_details.fragment.di

import dagger.BindsInstance
import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_friend_details.fragment.FriendDetailsFragment
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase
import javax.inject.Scope

@FriendDetailsScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [FriendDetailsLocalDiModule::class]
)
interface FriendDetailsComponent {

    fun inject(friendDetailsFragment: FriendDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            communityComponent: CommunityComponent,
            @BindsInstance getUserPostsUseCase: GetUserPostsUseCase,
            @BindsInstance getUserFullDataUseCase: GetUserFullDataUseCase,
            @BindsInstance toggleUserPostLikeUseCase: ToggleUserPostLikeUseCase,
            @BindsInstance markUserPostViewedUseCase: MarkUserPostViewedUseCase
        ): FriendDetailsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FriendDetailsScope
