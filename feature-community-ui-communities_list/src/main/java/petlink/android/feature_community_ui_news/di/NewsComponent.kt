package petlink.android.feature_community_ui_news.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_news.CommunitiesListFragment
import javax.inject.Scope

@NewsFragmentScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [NewsLocalDiModule::class]
)
interface NewsComponent {

    fun inject(fragment: CommunitiesListFragment)

    @Component.Factory
    interface Factory{
        fun create(communityComponent: CommunityComponent): NewsComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NewsFragmentScope()