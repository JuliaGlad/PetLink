package petlink.android.feature_community_ui_create_community.fragment.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityLocalDI

@Module
class CreateNewsCommunityLocalDiModule {

    @CreateNewsCommunityScope
    @Provides
    fun provideCreateNewsCommunityLocalDi(
        addNewsCommunityUseCase: AddNewsCommunityUseCase
    ): CreateNewsCommunityLocalDI = CreateNewsCommunityLocalDI(addNewsCommunityUseCase)

}