package petlink.android.core_di.community.modules

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import petlink.android.core_di.community.component.CommunityScope
import petlink.android.feature_community_data_impl.local_db.db.NewsCommunityDatabase

@Module
class CommunityDatabaseModule {

    @CommunityScope
    @Provides
    fun provideCommunityLocalDatabase(context: Context): NewsCommunityDatabase =
        databaseBuilder(context, NewsCommunityDatabase::class.java, "news_community_database").build()

}