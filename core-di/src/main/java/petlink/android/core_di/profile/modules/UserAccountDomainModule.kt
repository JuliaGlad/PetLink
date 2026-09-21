package petlink.android.core_di.profile.modules

import dagger.Binds
import dagger.Module
import dagger.Reusable
import petlink.android.core_di.profile.component.ProfileScope
import petlink.android.feature_profile_domain.usecase.user_account.AddUserDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.AddUserPostCommentUseCase
import petlink.android.feature_profile_domain.usecase.user_account.CreateUserPostUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditPetDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostCommentsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostCommentLikeUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import petlink.android.feature_profile_domain_impl.usecase.user_account.AddUserDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.AddUserPostCommentUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.CreateUserPostUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.EditOwnerDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.EditPetDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserFullDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserMainDataDomainUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserPostCommentsUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserPostsUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.MarkUserPostViewedUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.ToggleUserPostCommentLikeUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.ToggleUserPostLikeUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.UpdateBackgroundUseCaseImpl

@Module
interface UserAccountDomainModule {

    @ProfileScope
    @Binds
    fun bindAddUserDataUseCase(addUserDataUseCaseImpl: AddUserDataUseCaseImpl): AddUserDataUseCase

    @ProfileScope
    @Binds
    fun bindEditOwnerDataUseCase(editOwnerDataUseCaseImpl: EditOwnerDataUseCaseImpl): EditOwnerDataUseCase

    @ProfileScope
    @Binds
    fun bindEditPetDataUseCase(editPetDataUseCaseImpl: EditPetDataUseCaseImpl): EditPetDataUseCase

    @ProfileScope
    @Binds
    fun bindGetUserFullDataUseCase(getUserFullDataUseCaseImpl: GetUserFullDataUseCaseImpl): GetUserFullDataUseCase

    @ProfileScope
    @Binds
    fun bindGetUserMainDataUseCase(getUserMainDataDomainUseCaseImpl: GetUserMainDataDomainUseCaseImpl): GetUserMainDataDomainUseCase

    @ProfileScope
    @Binds
    fun bindUpdateBackgroundUseCase(updateBackgroundUseCaseImpl: UpdateBackgroundUseCaseImpl): UpdateBackgroundUseCase

    @ProfileScope
    @Binds
    fun bindCreateUserPostUseCase(createUserPostUseCaseImpl: CreateUserPostUseCaseImpl): CreateUserPostUseCase

    @ProfileScope
    @Binds
    fun bindGetUserPostsUseCase(getUserPostsUseCaseImpl: GetUserPostsUseCaseImpl): GetUserPostsUseCase

    @ProfileScope
    @Binds
    fun bindToggleUserPostLikeUseCase(
        toggleUserPostLikeUseCaseImpl: ToggleUserPostLikeUseCaseImpl
    ): ToggleUserPostLikeUseCase

    @ProfileScope
    @Binds
    fun bindMarkUserPostViewedUseCase(
        markUserPostViewedUseCaseImpl: MarkUserPostViewedUseCaseImpl
    ): MarkUserPostViewedUseCase

    @ProfileScope
    @Binds
    fun bindGetUserPostCommentsUseCase(
        getUserPostCommentsUseCaseImpl: GetUserPostCommentsUseCaseImpl
    ): GetUserPostCommentsUseCase

    @ProfileScope
    @Binds
    fun bindAddUserPostCommentUseCase(
        addUserPostCommentUseCaseImpl: AddUserPostCommentUseCaseImpl
    ): AddUserPostCommentUseCase

    @ProfileScope
    @Binds
    fun bindToggleUserPostCommentLikeUseCase(
        toggleUserPostCommentLikeUseCaseImpl: ToggleUserPostCommentLikeUseCaseImpl
    ): ToggleUserPostCommentLikeUseCase

}