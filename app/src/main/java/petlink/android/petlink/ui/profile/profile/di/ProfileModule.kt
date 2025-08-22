package petlink.android.petlink.ui.profile.profile.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl

@Module
interface ProfileModule {

    @ProfileScope
    @Binds
    fun bindUserAccountRepository(userAccountRepositoryImpl: UserAccountRepositoryImpl): UserAccountRepository

    @ProfileScope
    @Binds
    fun bindUserLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource
}