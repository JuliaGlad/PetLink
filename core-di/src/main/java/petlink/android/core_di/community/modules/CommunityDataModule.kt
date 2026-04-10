package petlink.android.core_di.community.modules

import dagger.Binds
import dagger.Module
import petlink.android.core_di.community.component.CommunityScope
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_data_impl.local_source.NewsCommunityLocalSourceImpl
import petlink.android.feature_community_data_impl.repository.NewsCommunityRepositoryImpl

@Module
interface CommunityDataModule {

    @CommunityScope
    @Binds
    fun bindNewsCommunityRepository(
        newsCommunityRepositoryImpl: NewsCommunityRepositoryImpl
    ): NewsCommunityRepository

    @CommunityScope
    @Binds
    fun bindNewsCommunityLocalSource(
        newsCommunityLocalSourceImpl: NewsCommunityLocalSourceImpl
    ): NewsCommunityLocalSource
}