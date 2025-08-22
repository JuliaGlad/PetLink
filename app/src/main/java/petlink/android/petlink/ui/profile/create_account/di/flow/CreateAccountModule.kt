package petlink.android.petlink.ui.profile.create_account.di.flow

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepositoryImpl
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl

@Module
interface CreateAccountModule {

    @CreateAccountScope
    @Binds
    fun bindUserAuthRepository(userAuthRepositoryImpl: UserAuthRepositoryImpl): UserAuthRepository

    @CreateAccountScope
    @Binds
    fun bindUserAccountRepository(userAccountRepositoryImpl: UserAccountRepositoryImpl): UserAccountRepository

    @CreateAccountScope
    @Binds
    fun bindUserLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource
}