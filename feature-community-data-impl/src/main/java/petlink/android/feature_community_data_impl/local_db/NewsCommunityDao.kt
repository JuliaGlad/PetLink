package petlink.android.feature_community_data_impl.local_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import petlink.android.feature_community_data.local_source.NewsCommunityEntity

@Dao
interface NewsCommunityDao {

    @Query("SELECT * FROM news_community")
    suspend fun getNewsCommunities(): List<NewsCommunityEntity>

    @Insert
    suspend fun insertNewsCommunity(entity: NewsCommunityEntity)

    @Query("DELETE FROM news_community")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNewsCommunity(communityEntity: NewsCommunityEntity)
}