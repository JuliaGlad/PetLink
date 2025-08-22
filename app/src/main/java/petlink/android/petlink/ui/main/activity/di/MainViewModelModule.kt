package petlink.android.petlink.ui.main.activity.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.ui.main.activity.MainViewModel
import javax.inject.Provider

@Module
class MainViewModelModule {

    @MainActivityScope
    @Provides
    fun provideMainViewModelFactory(
        provider: Provider<MainViewModel>
    ): MainViewModel.Factory {
        return MainViewModel.Factory(provider)
    }

}