package petlink.android.core_di.community.modules

import dagger.Binds
import dagger.Module
import petlink.android.core_di.community.component.CommunityScope
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.DeleteCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import petlink.android.feature_community_domain_impl.AddNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.DeleteCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.GetNewsCommunityByIdUseCaseImpl
import petlink.android.feature_community_domain_impl.GetNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.GetOwnedCommunitiesUseCaseImpl
import petlink.android.feature_community_domain_impl.GetSubscribedCommunitiesUseCaseImpl
import petlink.android.feature_community_domain_impl.SubscribeToNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.UnsubscribeFromNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.UpdateNewsCommunityAvatarUseCaseImpl
import petlink.android.feature_community_domain_impl.UpdateNewsCommunityBackgroundUseCaseImpl
import petlink.android.feature_community_domain_impl.UpdateNewsCommunityUseCaseImpl

@Module
interface CommunityDomainModule {

    @CommunityScope
    @Binds
    fun bindGetSubscribedCommunitiesUseCase(
        getSubscribedCommunitiesUseCaseImpl: GetSubscribedCommunitiesUseCaseImpl
    ): GetSubscribedCommunitiesUseCase

    @CommunityScope
    @Binds
    fun bindGetOwnedCommunitiesUseCase(
        getOwnedCommunitiesUseCaseImpl: GetOwnedCommunitiesUseCaseImpl
    ): GetOwnedCommunitiesUseCase

    @CommunityScope
    @Binds
    fun bindAddNewsCommunityUseCase(
        addNewsCommunityUseCaseImpl: AddNewsCommunityUseCaseImpl
    ): AddNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindDeleteCommunityUseCase(
        deleteCommunityUseCaseImpl: DeleteCommunityUseCaseImpl
    ): DeleteCommunityUseCase

    @CommunityScope
    @Binds
    fun bindGetNewsCommunityByIdUseCase(
        getNewsCommunityByIdUseCaseImpl: GetNewsCommunityByIdUseCaseImpl
    ): GetNewsCommunityByIdUseCase

    @CommunityScope
    @Binds
    fun bindGetNewsCommunityUseCase(
        getNewsCommunityUseCaseImpl: GetNewsCommunityUseCaseImpl
    ): GetNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindSubscribeToNewsCommunityUseCase(
        subscribeToNewsCommunityUseCaseImpl: SubscribeToNewsCommunityUseCaseImpl
    ): SubscribeToNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindUnsubscribeFromNewsCommunityUseCase(
        unsubscribeFromNewsCommunityUseCaseImpl: UnsubscribeFromNewsCommunityUseCaseImpl
    ): UnsubscribeFromNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindUpdateNewsCommunityUseCase(
        updateNewsCommunityUseCaseImpl: UpdateNewsCommunityUseCaseImpl
    ): UpdateNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindUpdateNewsCommunityAvatarUseCase(
        updateNewsCommunityAvatarUseCaseImpl: UpdateNewsCommunityAvatarUseCaseImpl
    ): UpdateNewsCommunityAvatarUseCase

    @CommunityScope
    @Binds
    fun bindUpdateNewsCommunityBackgroundUseCase(
        updateNewsCommunityBackgroundUseCaseImpl: UpdateNewsCommunityBackgroundUseCaseImpl
    ): UpdateNewsCommunityBackgroundUseCase
}