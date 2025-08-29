package petlink.android.petlink.ui.profile.settings.dialog.logout.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl

@Module
interface LogoutModule {

    @LogoutScope
    @Binds
    fun bindUserAuthRepository(authRepositoryImpl: UserAuthRepositoryImpl): UserAuthRepository

    @LogoutScope
    @Binds
    fun bindUserLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource
}