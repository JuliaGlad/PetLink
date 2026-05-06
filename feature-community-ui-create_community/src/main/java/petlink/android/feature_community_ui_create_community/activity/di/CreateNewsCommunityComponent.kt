package petlink.android.feature_community_ui_create_community.activity.di

import dagger.Component
import petlink.android.core_di.app.AppComponent
import petlink.android.feature_community_ui_create_community.activity.CreateNewsCommunityActivity
import javax.inject.Scope

@CreateNewsCommunityScope
@Component(dependencies = [AppComponent::class])
interface CreateNewsCommunityActivityComponent {

    fun inject(activity: CreateNewsCommunityActivity)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): CreateNewsCommunityActivityComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateNewsCommunityScope