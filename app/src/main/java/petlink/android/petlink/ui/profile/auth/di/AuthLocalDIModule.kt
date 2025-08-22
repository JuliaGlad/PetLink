package petlink.android.petlink.ui.profile.auth.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.ui.profile.auth.mvi.AuthLocalDI

@Module
class AuthLocalDIModule {

    @AuthScope
    @Provides
    fun provideAuthLocalDi(
        userAuthRepository: UserAuthRepository
    ): AuthLocalDI = AuthLocalDI(userAuthRepository)

}