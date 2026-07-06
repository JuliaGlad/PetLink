package petlink.android.feature_community_ui_create_community.fragment.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_create_community.fragment.CreateNewsCommunityFragment
import javax.inject.Scope

@CreateNewsCommunityScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [CreateNewsCommunityLocalDiModule::class]
)
interface CreateNewsCommunityComponent {

    fun inject(fragment: CreateNewsCommunityFragment)

    @Component.Factory
    interface Factory{
        fun create(component: CommunityComponent): CreateNewsCommunityComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateNewsCommunityScope