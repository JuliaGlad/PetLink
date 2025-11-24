package petlink.android.feature_profile_ui_settings.dialog.logout.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_ui_settings.dialog.logout.LogoutViewModel
import javax.inject.Provider

@Module
class LogoutViewModelModule {

    @LogoutScope
    @Provides
    fun provideLogoutViewModelFactory(
        provider: Provider<LogoutViewModel>
    ): LogoutViewModel.Factory {
        return LogoutViewModel.Factory(provider)
    }

}