package petlink.android.feature_community_data_impl.local_db

import petlink.android.feature_community_data.local_source.NewsCommunityEntity
import petlink.android.feature_community_data_impl.local_db.db.NewsCommunityDatabase
import javax.inject.Inject
import kotlin.reflect.KMutableProperty0

class NewsCommunityProvider @Inject constructor(
    private val database: NewsCommunityDatabase
) {
    suspend fun getCommunities(): List<NewsCommunityEntity>? = database.newsCommunityDao().getNewsCommunities()

    suspend fun insertCommunity(
        communityId: String,
        title: String,
        description: String,
        avatar: String
    ) {
        database.newsCommunityDao().insertNewsCommunity(
            NewsCommunityEntity(
                communityId = communityId,
                title = title,
                description = description,
                avatar = avatar
            )
        )
    }

    suspend fun updateCommunity(
        id: String,
        title: String,
        description: String,
        avatar: String
    ){
        val dao = database.newsCommunityDao()
        dao.getNewsCommunities()?.forEach {
            if (it.communityId == id){
                with(it) {
                    updateIfChanged(::title, title)
                    updateIfChanged(::description, description)
                    updateIfChanged(::avatar, avatar)
                }
                dao.updateNewsCommunity(it)
            }
        }
    }

    suspend fun deleteCommunity(id: String){
        val dao = database.newsCommunityDao()
        dao.getNewsCommunities()?.forEach {
            if (it.communityId == id){
                dao.deleteNewsCommunity(it)
            }
        }
    }

    suspend fun deleteAll(){
        database.newsCommunityDao().deleteAll()
    }

    private fun <T> updateIfChanged(prev: KMutableProperty0<T>, new: T?) {
        if (prev != new) {
            prev.set(new as T)
        }
    }

}