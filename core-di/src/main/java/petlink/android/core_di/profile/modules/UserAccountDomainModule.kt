package petlink.android.core_di.profile.modules

import dagger.Binds
import dagger.Module
import dagger.Reusable
import petlink.android.core_di.profile.component.ProfileScope
import petlink.android.feature_profile_domain.usecase.user_account.AddUserDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditPetDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import petlink.android.feature_profile_domain_impl.usecase.user_account.AddUserDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.EditOwnerDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.EditPetDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserFullDataUseCaseImpl
import petlink.android.feature_profile_domain_impl.usecase.user_account.GetUserMainDataDomainUseCaseImpl
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

}