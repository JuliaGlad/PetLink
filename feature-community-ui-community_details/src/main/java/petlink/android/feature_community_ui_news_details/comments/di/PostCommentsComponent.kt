package petlink.android.feature_community_ui_news_details.comments.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_news_details.comments.PostCommentsBottomSheet
import javax.inject.Scope

@PostCommentsScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [PostCommentsLocalDiModule::class]
)
interface PostCommentsComponent {

    fun inject(postCommentsBottomSheet: PostCommentsBottomSheet)

    @Component.Factory
    interface Factory {
        fun create(communityComponent: CommunityComponent): PostCommentsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PostCommentsScope
