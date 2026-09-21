package petlink.android.feature_profile_ui_friends.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_profile_ui_friends.FriendFragment
import javax.inject.Scope

@FriendsScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [FriendsLocalDiModule::class]
)
interface FriendsComponent {

    fun inject(fragment: FriendFragment)

    @Component.Factory
    interface Factory {
        fun create(communityComponent: CommunityComponent): FriendsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FriendsScope
