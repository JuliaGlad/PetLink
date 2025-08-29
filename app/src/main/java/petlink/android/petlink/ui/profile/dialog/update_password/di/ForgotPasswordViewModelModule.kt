package petlink.android.petlink.ui.profile.dialog.update_password.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.ui.profile.dialog.update_password.UpdatePasswordViewModel
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