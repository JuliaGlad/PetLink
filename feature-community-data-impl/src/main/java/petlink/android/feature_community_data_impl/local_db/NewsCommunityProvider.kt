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
        ownerId: String,
        subscribers: MutableList<String>,
        title: String,
        description: String,
        avatar: String,
        background: String
    ) {
        database.newsCommunityDao().insertNewsCommunity(
            NewsCommunityEntity(
                communityId = communityId,
                title = title,
                description = description,
                avatar = avatar,
                subscribers = subscribers,
                ownerId = ownerId,
                background = background
            )
        )
    }

    suspend fun addSubscriber(id: String, userId: String){
        val dao = database.newsCommunityDao()
        dao.getNewsCommunities()?.forEach {
            if (it.communityId == id){
                with(it) {
                    if (!subscribers.contains(userId)) subscribers.add(userId)
                    dao.updateNewsCommunity(it)
                }
            }
        }
    }

    suspend fun removeSubscriber(id: String, userId: String){
        val dao = database.newsCommunityDao()
        dao.getNewsCommunities()?.forEach {
            if (it.communityId == id){
                with(it) {
                    subscribers.remove(userId)
                    dao.updateNewsCommunity(it)
                }
            }
        }
    }

    suspend fun updateCommunity(
        id: String,
        title: String?,
        description: String?
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

    suspend fun updateCommunityAvatar(
        id: String,
        newUri: String
    ){
        val dao = database.newsCommunityDao()
        dao.getNewsCommunities()?.forEach {
            if (it.communityId == id){
                with(it) {
                    updateIfChanged(::avatar, newUri)
                }
                dao.updateNewsCommunity(it)
            }
        }
    }
    suspend fun updateCommunityBackground(
        id: String,
        newUri: String
    ){
        val dao = database.newsCommunityDao()
        dao.getNewsCommunities()?.forEach {
            if (it.communityId == id){
                with(it) {
                    updateIfChanged(::background, newUri)
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
        if (prev != new && new != null) {
            prev.set(new as T)
        }
    }

}