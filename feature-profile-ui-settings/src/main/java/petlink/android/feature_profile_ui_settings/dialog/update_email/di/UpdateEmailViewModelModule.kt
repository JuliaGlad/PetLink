package petlink.android.feature_profile_ui_settings.dialog.update_email.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_ui_settings.dialog.update_email.UpdateEmailViewModel
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