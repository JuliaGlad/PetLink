package petlink.android.feature_community_ui_main.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_ui_main.mvi.CommunityMainLocalDI

@Module
class CommunityMainDiModule {

    @CommunityMainScope
    @Provides
    fun provideCommunityMainLocalDi(): CommunityMainLocalDI = CommunityMainLocalDI()

}