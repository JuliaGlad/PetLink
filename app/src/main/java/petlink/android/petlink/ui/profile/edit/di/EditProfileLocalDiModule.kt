package petlink.android.petlink.ui.profile.edit.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.ui.profile.edit.mvi.EditProfileLocalDI

@Module
class EditProfileLocalDiModule {

    @EditProfileScope
    @Provides
    fun provideEditProfileLocalDI(
        userAccountRepository: UserAccountRepository
    ) = EditProfileLocalDI(userAccountRepository)

}