package petlink.android.petlink.ui.profile.profile.di

import dagger.Module
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.ui.profile.profile.mvi.ProfileLocalDI

@Module
class ProfileLocalDIModule {

    fun provideProfileLocalDI(
        userAccountRepository: UserAccountRepository
    ): ProfileLocalDI = ProfileLocalDI(userAccountRepository)

}