package petlink.android.feature_community_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import javax.inject.Inject
import kotlin.random.Random

class NewsCommunityRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: NewsCommunityLocalSource
): NewsCommunityRepository {
    override suspend fun getNewsCommunity(): List<NewsCommunityDto> {
        val query = store.collection(NEWS_COMMUNITY)
            .get()
            .await()
        val local = localSource.getNewsCommunity()
        if (local != null) return local
        return query.map {
            with(it) {
                NewsCommunityDto(
                    id = id,
                    title = getString(COMMUNITY_TITLE).toString(),
                    description = getString(COMMUNITY_DESCRIPTION).toString(),
                    avatar = getString(COMMUNITY_AVATAR).toString(),
                    subscribersCount = getString(COMMUNITY_SUBSCRIBERS).toString().toInt()
                )
            }
        }.toList()
    }

    override suspend fun addNewsCommunity(
        title: String,
        description: String,
        avatar: String
    ) {
        val id = generateId().toString()
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .set(
                hashMapOf(
                    COMMUNITY_ID to id,
                    COMMUNITY_TITLE to title,
                    COMMUNITY_DESCRIPTION to description,
                    COMMUNITY_AVATAR to avatar,
                    COMMUNITY_SUBSCRIBERS to "0"
                )
            ).await()
        localSource.insertNewsCommunity(
            communityId = id,
            title = title,
            description = description,
            avatar = avatar,
            subscribersCount = 0
        )
    }

    override suspend fun updateNewsCommunityData(
        id: String,
        newTitle: String?,
        newDescription: String?,
        newAvatar: String?
    ) {
        val updates = mapOf(
            COMMUNITY_TITLE to newTitle,
            COMMUNITY_DESCRIPTION to newDescription,
            COMMUNITY_AVATAR to newAvatar
        )
        updateCommunityDataFields(id, updates)
        localSource.updateNewsCommunityData(
            communityId = id,
            newTitle = newTitle,
            newDescription = newDescription,
            newAvatar = newAvatar
        )
    }

    override suspend fun deleteNewsCommunity(id: String) {
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .delete()
            .await()
    }

    override suspend fun subscribeToCommunity(id: String) {
        auth.currentUser?.let {
            store.collection(USER_COLLECTION)
                .document(it.uid)
                .update(SUBSCRIBED_IDS, FieldValue.arrayUnion(id))
                .await()
        }
        val subscribersCount = store.collection(NEWS_COMMUNITY).document(id).get().await().getString(COMMUNITY_SUBSCRIBERS)?.toInt() ?: 0
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .update(COMMUNITY_SUBSCRIBERS, (subscribersCount + 1).toString())
            .await()
    }

    override suspend fun unsubscribeFromCommunity(id: String) {
        auth.currentUser?.let {
            store.collection(USER_COLLECTION)
                .document(it.uid)
                .update(SUBSCRIBED_IDS, FieldValue.arrayRemove(id))
                .await()
        }
        val subscribersCount = store.collection(NEWS_COMMUNITY).document(id).get().await().getString(COMMUNITY_SUBSCRIBERS)?.toInt() ?: 0
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .update(COMMUNITY_SUBSCRIBERS, (subscribersCount - 1).toString())
            .await()
        localSource.removeSubscriber(id)
    }

    private suspend fun updateCommunityDataFields(id: String, updates: Map<String, Any?>) {
        val filteredUpdates = updates.filterValues { it != null }.mapValues { it.value!! }
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .update(filteredUpdates)
            .await()
        localSource.addSubscriber(id)
    }

    private fun generateId() = Random.nextInt()

    companion object{
        const val USER_COLLECTION = "Users"
        const val NEWS_COMMUNITY = "news_community"
        const val COMMUNITY_SUBSCRIBERS = "community_subscribers"
        const val COMMUNITY_ID = "community_id"
        const val COMMUNITY_TITLE = "community_title"
        const val COMMUNITY_DESCRIPTION = "community_description"
        const val COMMUNITY_AVATAR = "community_avatar"
        const val SUBSCRIBED_IDS = "subscribed_ids"
    }

}