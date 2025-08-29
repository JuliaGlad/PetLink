package petlink.android.petlink.ui.profile.settings.dialog.logout.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.ui.profile.settings.dialog.delete_account.DeleteAccountViewModel
import petlink.android.petlink.ui.profile.settings.dialog.logout.LogoutViewModel
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