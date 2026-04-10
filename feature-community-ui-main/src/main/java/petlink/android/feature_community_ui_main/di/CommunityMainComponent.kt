package petlink.android.feature_community_ui_main.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_main.CommunityMainFragment
import javax.inject.Scope

@CommunityMainScope
@Component(
    modules = [CommunityMainDiModule::class],
    dependencies = [CommunityComponent::class]
)
interface CommunityMainComponent {

    fun inject(fragment: CommunityMainFragment)

    @Component.Factory
    interface Factory{
        fun create(communityComponent: CommunityComponent): CommunityMainComponent
    }

}

@Scope
annotation class CommunityMainScope