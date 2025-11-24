package petlink.android.feature_profile_ui_settings.dialog.delete_account.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_ui_settings.dialog.delete_account.DeleteAccountViewModel
import javax.inject.Provider

@Module
class DeleteViewModelModule {

    @DeleteAccountScope
    @Provides
    fun provideDeleteAccountViewModelFactory(
        provider: Provider<DeleteAccountViewModel>
    ): DeleteAccountViewModel.Factory {
        return DeleteAccountViewModel.Factory(provider)
    }

}