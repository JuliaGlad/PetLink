package petlink.android.petlink.ui.profile.auth.dialog.forgot_password.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.ui.profile.auth.dialog.forgot_password.ForgotPasswordViewModel
import javax.inject.Provider

@Module
class ForgotPasswordViewModelModule {

    @ForgotPasswordScope
    @Provides
    fun provideForgotPasswordViewModelFactory(
        provider: Provider<ForgotPasswordViewModel>
    ): ForgotPasswordViewModel.Factory {
        return ForgotPasswordViewModel.Factory(provider)
    }

}