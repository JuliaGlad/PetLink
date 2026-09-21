package petlink.android.feature_community_ui_news_details.delete_dialog.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_ui_news_details.delete_dialog.DeleteCommunityViewModel
import javax.inject.Provider

@Module
class DeleteCommunityViewModelModule {

    @DeleteCommunityScope
    @Provides
    fun provideDeleteAccountViewModelFactory(
        provider: Provider<DeleteCommunityViewModel>
    ): DeleteCommunityViewModel.Factory {
        return DeleteCommunityViewModel.Factory(provider)
    }

}