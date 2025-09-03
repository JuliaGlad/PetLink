package petlink.android.petlink.ui.profile.my_data.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl

@Module
interface MyDataModule {

    @MyDataScope
    @Binds
    fun bindAccountRepository(accountRepositoryImpl: UserAccountRepositoryImpl): UserAccountRepository

    @MyDataScope
    @Binds
    fun bindAccountLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource

}