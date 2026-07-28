package petlink.android.feature_community_ui_news_details.details_bottomsheet.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_ui_news_details.details_bottomsheet.CommunityDataViewModel
import javax.inject.Provider

@Module
class CommunityDataViewModelModule {

    @CommunityDataScope
    @Provides
    fun provideCommunityDataViewModelFactory(
        provider: Provider<CommunityDataViewModel>
    ): CommunityDataViewModel.Factory {
        return CommunityDataViewModel.Factory(provider)
    }
}