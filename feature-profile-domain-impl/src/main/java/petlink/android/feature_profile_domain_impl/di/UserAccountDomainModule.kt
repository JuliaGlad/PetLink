package petlink.android.feature_profile_domain_impl.di

import dagger.Binds
import dagger.Module
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

    @Binds
    fun bindAddUserDataUseCase(addUserDataUseCaseImpl: AddUserDataUseCaseImpl): AddUserDataUseCase

    @Binds
    fun bindEditOwnerDataUseCase(editOwnerDataUseCaseImpl: EditOwnerDataUseCaseImpl): EditOwnerDataUseCase

    @Binds
    fun bindEditPetDataUseCase(editPetDataUseCaseImpl: EditPetDataUseCaseImpl): EditPetDataUseCase

    @Binds
    fun bindGetUserFullDataUseCase(getUserFullDataUseCaseImpl: GetUserFullDataUseCaseImpl): GetUserFullDataUseCase

    @Binds
    fun bindGetUserMainDataUseCase(getUserMainDataDomainUseCaseImpl: GetUserMainDataDomainUseCaseImpl): GetUserMainDataDomainUseCase

    @Binds
    fun bindUpdateBackgroundUseCase(updateBackgroundUseCaseImpl: UpdateBackgroundUseCaseImpl): UpdateBackgroundUseCase

}