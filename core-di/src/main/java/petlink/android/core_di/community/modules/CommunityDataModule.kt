package petlink.android.core_di.community.modules

import dagger.Binds
import dagger.Module
import petlink.android.core_di.community.component.CommunityScope
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import petlink.android.feature_community_data.repository.PhotosCommunityRepository
import petlink.android.feature_community_data.repository.QuestionsCommunityRepository
import petlink.android.feature_community_data_impl.local_source.NewsCommunityLocalSourceImpl
import petlink.android.feature_community_data_impl.repository.ChatRepositoryImpl
import petlink.android.feature_community_data_impl.repository.NewsCommunityRepositoryImpl
import petlink.android.feature_community_data_impl.repository.PhotosCommunityRepositoryImpl
import petlink.android.feature_community_data_impl.repository.QuestionsCommunityRepositoryImpl

@Module
interface CommunityDataModule {

    @CommunityScope
    @Binds
    fun bindNewsCommunityRepository(
        newsCommunityRepositoryImpl: NewsCommunityRepositoryImpl
    ): NewsCommunityRepository

    @CommunityScope
    @Binds
    fun bindPhotosCommunityRepository(
        photosCommunityRepositoryImpl: PhotosCommunityRepositoryImpl
    ): PhotosCommunityRepository

    @CommunityScope
    @Binds
    fun bindQuestionsCommunityRepository(
        questionsCommunityRepositoryImpl: QuestionsCommunityRepositoryImpl
    ): QuestionsCommunityRepository

    @CommunityScope
    @Binds
    fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @CommunityScope
    @Binds
    fun bindNewsCommunityLocalSource(
        newsCommunityLocalSourceImpl: NewsCommunityLocalSourceImpl
    ): NewsCommunityLocalSource
}
