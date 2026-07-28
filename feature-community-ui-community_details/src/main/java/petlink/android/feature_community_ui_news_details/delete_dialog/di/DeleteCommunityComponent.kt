package petlink.android.feature_community_ui_news_details.delete_dialog.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_community_ui_news_details.delete_dialog.DeleteCommunityDialogFragment
import javax.inject.Scope

@DeleteCommunityScope
@Component(
    dependencies = [CommunityComponent::class],
    modules = [DeleteCommunityViewModelModule::class]
)
interface DeleteCommunityComponent {

    fun inject(dialogFragment: DeleteCommunityDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(communityComponent: CommunityComponent): DeleteCommunityComponent
    }

}

@Scope
annotation class DeleteCommunityScope