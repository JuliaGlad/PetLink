package petlink.android.petlink.ui.profile.auth.di

import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl

@Module
interface AuthModule {

    @AuthScope
    @Binds
    fun bindUserLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource

    @AuthScope
    @Binds
    fun bindUserAuthRepository(userAuthRepositoryImpl: UserAuthRepositoryImpl): UserAuthRepository
}