package petlink.android.petlink.ui.profile.settings.dialog.delete_account.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.ui.profile.settings.dialog.delete_account.DeleteAccountViewModel
import petlink.android.petlink.ui.profile.settings.dialog.update_email.UpdateEmailViewModel
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