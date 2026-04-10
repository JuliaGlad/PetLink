package petlink.android.feature_community_ui_news.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_news.NewsFragment
import javax.inject.Scope

@NewsFragmentScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [NewsLocalDiModule::class]
)
interface NewsComponent {

    fun inject(fragment: NewsFragment)

    @Component.Factory
    interface Factory{
        fun create(profileComponent: CommunityComponent): NewsComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NewsFragmentScope()