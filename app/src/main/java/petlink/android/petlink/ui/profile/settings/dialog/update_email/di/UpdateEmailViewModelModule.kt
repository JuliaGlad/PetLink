package petlink.android.petlink.ui.profile.settings.dialog.update_email.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.ui.profile.dialog.update_password.UpdatePasswordViewModel
import petlink.android.petlink.ui.profile.settings.dialog.update_email.UpdateEmailViewModel
import javax.inject.Provider

@Module
class UpdateEmailViewModelModule {

    @UpdateEmailScope
    @Provides
    fun provideUpdateEmailViewModelFactory(
        provider: Provider<UpdateEmailViewModel>
    ): UpdateEmailViewModel.Factory {
        return UpdateEmailViewModel.Factory(provider)
    }

}