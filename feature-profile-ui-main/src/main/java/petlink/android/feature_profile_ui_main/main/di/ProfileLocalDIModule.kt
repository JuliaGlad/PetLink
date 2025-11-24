package petlink.android.feature_profile_ui_main.main.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import petlink.android.feature_profile_ui_main.main.mvi.ProfileLocalDI

@Module
class ProfileLocalDIModule {

    @ProfileScope
    @Provides
    fun provideProfileLocalDI(
        getUserDataUseCase: GetUserMainDataDomainUseCase,
        updateBackgroundUseCase: UpdateBackgroundUseCase
    ): ProfileLocalDI = ProfileLocalDI(
        getUserDataUseCase,
        updateBackgroundUseCase
    )

}