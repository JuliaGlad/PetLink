package petlink.android.petlink.ui.profile.create_account.di.flow

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountLocalDI

@Module
class CreateAccountLocalDIModule {

    @CreateAccountScope
    @Provides
    fun provideCreateAccountLocalDi(
        authRepository: UserAuthRepository,
        accountRepository: UserAccountRepository
    ): CreateAccountLocalDI = CreateAccountLocalDI(authRepository, accountRepository)

}