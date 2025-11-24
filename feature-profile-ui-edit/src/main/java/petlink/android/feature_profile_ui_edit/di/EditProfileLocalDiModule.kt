package petlink.android.feature_profile_ui_edit.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditPetDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_ui_edit.mvi.EditProfileLocalDI

@Module
class EditProfileLocalDiModule {

    @EditProfileScope
    @Provides
    fun provideEditProfileLocalDI(
        getUserFullDataUseCase: GetUserFullDataUseCase,
        editPetDataUseCase: EditPetDataUseCase,
        editOwnerDataUseCase: EditOwnerDataUseCase
    ) = EditProfileLocalDI(
        getUserFullDataUseCase = getUserFullDataUseCase,
        editPetDataUseCase = editPetDataUseCase,
        editOwnerDataUseCase = editOwnerDataUseCase
    )

}