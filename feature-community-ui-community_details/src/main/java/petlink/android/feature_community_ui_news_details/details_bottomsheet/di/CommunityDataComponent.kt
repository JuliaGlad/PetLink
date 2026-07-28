package petlink.android.feature_community_ui_news_details.details_bottomsheet.di

import dagger.Component
import petlink.android.core_di.community.component.CommunityComponent
import petlink.android.feature_community_ui_news_details.details_bottomsheet.CommunityDataBottomSheet
import javax.inject.Scope

@CommunityDataScope
@Component(
    modules = [CommunityDataViewModelModule::class],
    dependencies = [CommunityComponent::class]
)
interface CommunityDataComponent {

    fun inject(bottomSheet: CommunityDataBottomSheet)

    @Component.Factory
    interface Factory {
        fun create(
            communityComponent: CommunityComponent
        ): CommunityDataComponent
    }
}
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CommunityDataScope