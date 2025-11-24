package petlink.android.petlink.di

import dagger.Module
import dagger.Provides
import petlink.android.petlink.activity.MainViewModel
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