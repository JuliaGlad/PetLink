package petlink.android.feature_community_ui_news_details.fragment.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_news_details.fragment.CommunityDetailsFragment
import javax.inject.Scope

@CommunityDetailsScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [CommunityDetailsLocalDiModule::class]
)
interface CommunityDetailsComponent {

    fun inject(communityDetailsFragment: CommunityDetailsFragment)

    @Component.Factory
    interface Factory{
        fun create(communityComponent: CommunityComponent): CommunityDetailsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CommunityDetailsScope