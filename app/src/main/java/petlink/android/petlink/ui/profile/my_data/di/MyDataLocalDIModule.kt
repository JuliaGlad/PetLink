package petlink.android.petlink.ui.profile.my_data.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataLocalDI

@Module
class MyDataLocalDIModule {

    @MyDataScope
    @Provides
    fun provideMyDataLocalDI(
        accountRepository: UserAccountRepository
    ): MyDataLocalDI = MyDataLocalDI(accountRepository)

}