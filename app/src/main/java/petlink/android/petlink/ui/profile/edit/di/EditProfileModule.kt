package petlink.android.petlink.ui.profile.edit.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl

@Module
interface EditProfileModule {

    @EditProfileScope
    @Binds
    fun bindAccountRepository(accountRepositoryImpl: UserAccountRepositoryImpl): UserAccountRepository

    @EditProfileScope
    @Binds
    fun bindAccountLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource
}