package petlink.android.feature_profile_ui_create_account.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_domain.usecase.user_account.AddUserDataUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.CreateUserUseCase
import petlink.android.feature_profile_ui_create_account.fragment.mvi.CreateAccountLocalDI

@Module
class CreateAccountLocalDIModule {

    @CreateAccountScope
    @Provides
    fun provideCreateAccountLocalDi(
        createUserUseCase: CreateUserUseCase,
        addUserDataUseCase: AddUserDataUseCase
    ): CreateAccountLocalDI = CreateAccountLocalDI(createUserUseCase, addUserDataUseCase)

}