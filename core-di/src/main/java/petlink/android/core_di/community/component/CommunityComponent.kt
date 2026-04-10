package petlink.android.core_di.community.component

import com.github.terrakok.cicerone.Router
import com.google.api.Context
import dagger.Component
import petlink.android.core_di.app.AppComponent
import petlink.android.core_di.community.modules.CommunityDataModule
import petlink.android.core_di.community.modules.CommunityDatabaseModule
import petlink.android.core_di.community.modules.CommunityDomainModule
import petlink.android.feature_community_data_impl.local_db.db.NewsCommunityDatabase
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.DeleteCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import javax.inject.Scope

@CommunityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CommunityDatabaseModule::class,
        CommunityDataModule::class,
        CommunityDomainModule::class
    ]
)
interface CommunityComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CommunityComponent
    }

    fun router(): Router

    fun getOwnedCommunitiesUseCase(): GetOwnedCommunitiesUseCase

    fun getSubscribedCommunitiesUseCase(): GetSubscribedCommunitiesUseCase

    fun addNewsCommunityUseCase(): AddNewsCommunityUseCase

    fun deleteCommunityUseCase(): DeleteCommunityUseCase

    fun getNewsCommunityByIdUseCase(): GetNewsCommunityByIdUseCase

    fun getNewsCommunityUseCase(): GetNewsCommunityUseCase

    fun subscribeToNewsCommunityUseCase(): SubscribeToNewsCommunityUseCase

    fun unsubscribeFromNewsCommunityUseCase(): UnsubscribeFromNewsCommunityUseCase

    fun updateNewsCommunityUseCase(): UpdateNewsCommunityUseCase
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CommunityScope