package petlink.android.feature_profile_ui_main.auth_fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignInUseCase
import petlink.android.feature_profile_ui_main.auth_fragment.mvi.AuthLocalDI

@Module
class AuthLocalDIModule {

    @AuthScope
    @Provides
    fun provideAuthLocalDi(
        signInUseCase: SignInUseCase,
        checkIsAuthenticatedUseCase: CheckIsAuthenticatedUseCase
    ): AuthLocalDI = AuthLocalDI(signInUseCase, checkIsAuthenticatedUseCase)

}