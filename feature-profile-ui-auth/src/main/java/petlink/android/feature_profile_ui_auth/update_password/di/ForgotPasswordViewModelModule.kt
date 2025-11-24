package petlink.android.feature_profile_ui_auth.update_password.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_ui_auth.update_password.UpdatePasswordViewModel
import javax.inject.Provider

@Module
class ForgotPasswordViewModelModule {

    @ForgotPasswordScope
    @Provides
    fun provideForgotPasswordViewModelFactory(
        provider: Provider<UpdatePasswordViewModel>
    ): UpdatePasswordViewModel.Factory {
        return UpdatePasswordViewModel.Factory(provider)
    }

}