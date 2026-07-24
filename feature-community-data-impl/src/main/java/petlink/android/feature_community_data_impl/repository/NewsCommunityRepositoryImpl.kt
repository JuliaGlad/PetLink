package petlink.android.feature_community_data_impl.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import javax.inject.Inject
import kotlin.collections.map
class NewsCommunityRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: NewsCommunityLocalSource
) : NewsCommunityRepository {
    override suspend fun getOwnedCommunities(): List<NewsCommunityDto> {
        val query = store.collection(NEWS_COMMUNITY)
            .whereEqualTo(OWNER, auth.currentUser?.uid)
            .get()
            .await()
        return query.map {
            with(it) {
                NewsCommunityDto(
                    id = id,
                    title = getString(COMMUNITY_TITLE).toString(),
                    description = getString(COMMUNITY_DESCRIPTION).toString(),
                    avatar = getString(COMMUNITY_AVATAR).toString(),
                    subscribers = (get(COMMUNITY_SUBSCRIBERS) as List<*>).mapNotNull { it as? String }
                        .toList(),
                    ownerId = getString(COMMUNITY_OWNER).toString(),
                    role = OWNER,
                    background = getString(BACKGROUND).toString()
                )
            }
        }
    }

    override suspend fun getSubscribedCommunities(): List<NewsCommunityDto> {
        val subscribedIds: List<String> = auth.currentUser?.let {
            val snapshot = store.collection(USER_COLLECTION)
                .document(it.uid)
                .get()
                .await()
            val ids = snapshot.get(SUBSCRIBED_IDS) as? List<*>
            ids
                ?.mapNotNull { it as? String }
                ?.toList()
                ?: listOf()
        } ?: emptyList()
        val result = if (subscribedIds.isNotEmpty()){
            val query = store.collection(NEWS_COMMUNITY)
                .whereIn(FieldPath.documentId(), subscribedIds)
                .get()
                .await()
            query.map {
                with(it) {
                    NewsCommunityDto(
                        id = id,
                        title = getString(COMMUNITY_TITLE).toString(),
                        description = getString(COMMUNITY_DESCRIPTION).toString(),
                        avatar = getString(COMMUNITY_AVATAR).toString(),
                        ownerId = getString(COMMUNITY_OWNER).toString(),
                        subscribers = (get(COMMUNITY_SUBSCRIBERS) as List<*>).mapNotNull { it as? String }
                            .toList(),
                        role = OWNER,
                        background = getString(BACKGROUND).toString()
                    )
                }
            }
        } else{
            emptyList()
        }
        return result
    }

    override suspend fun getNewsCommunity(): List<NewsCommunityDto> {
        val subscribedIds: List<String> = auth.currentUser?.let {
            val snapshot = store.collection(USER_COLLECTION)
                .document(it.uid)
                .get()
                .await()
            val ids = snapshot.get(SUBSCRIBED_IDS) as? List<*>
            ids
                ?.mapNotNull { it as? String }
                ?.toList()
                ?: listOf()
        } ?: emptyList()
        val query = if (subscribedIds.isNotEmpty()) {
            store.collection(NEWS_COMMUNITY)
                .whereNotEqualTo(OWNER, auth.currentUser?.uid)
                .whereNotIn(FieldPath.documentId(), subscribedIds)
                .get()
                .await()
        } else {
            store.collection(NEWS_COMMUNITY)
                .whereNotEqualTo(OWNER, auth.currentUser?.uid)
                .get()
                .await()
        }
        val result = query.filter { !subscribedIds.contains(it.id) }.map {
            with(it) {
                NewsCommunityDto(
                    id = id,
                    title = getString(COMMUNITY_TITLE).toString(),
                    description = getString(COMMUNITY_DESCRIPTION).toString(),
                    ownerId = getString(COMMUNITY_OWNER).toString(),
                    avatar = getString(COMMUNITY_AVATAR).toString(),
                    subscribers = (get(COMMUNITY_SUBSCRIBERS) as List<*>).mapNotNull { it as? String }
                        .toList(),
                    role = NONE,
                    background = getString(BACKGROUND).toString()
                )
            }
        }
        return result
    }

    override suspend fun addNewsCommunity(
        title: String,
        description: String,
        avatar: String,
        background: String
    ): String {
        val communityID = withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val id = generateId()
            store.collection(NEWS_COMMUNITY)
                .document(id)
                .set(
                    hashMapOf(
                        COMMUNITY_ID to id,
                        COMMUNITY_TITLE to title,
                        COMMUNITY_DESCRIPTION to description,
                        COMMUNITY_AVATAR to avatar,
                        COMMUNITY_OWNER to uid,
                        COMMUNITY_SUBSCRIBERS to emptyList<String>(),
                        BACKGROUND to background
                    )
                ).await()
            localSource.insertNewsCommunity(
                communityId = id,
                title = title,
                description = description,
                avatar = avatar,
                subscribers = mutableListOf<String>(),
                ownerId = uid,
                background = background
            )
            store.collection(USER_COLLECTION)
                .document(uid)
                .update(OWNED_NEWS_COMMUNITIES, FieldValue.arrayUnion(id))
                .await()
            id
        }
        return communityID
    }

    override suspend fun updateNewsCommunityData(
        id: String,
        newTitle: String?,
        newDescription: String?
    ) {
        val updates = mapOf(
            COMMUNITY_TITLE to newTitle,
            COMMUNITY_DESCRIPTION to newDescription
        )
        updateCommunityDataFields(id, updates)
        localSource.updateNewsCommunityData(
            communityId = id,
            newTitle = newTitle,
            newDescription = newDescription
        )
    }

    override suspend fun updateNewsCommunityAvatar(id: String, newUri: String) {
        val updates = mapOf(
            COMMUNITY_AVATAR to newUri
        )
        updateCommunityDataFields(id, updates)
        localSource.updateNewsCommunityAvatar(
            communityId = id,
            newAvatar = newUri
        )
    }

    override suspend fun updateNewsCommunityBackground(id: String, newUri: String) {
        val updates = mapOf(
            BACKGROUND to newUri
        )
        updateCommunityDataFields(id, updates)
        localSource.updateNewsCommunityBackground(
            communityId = id,
            newBackground = newUri
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
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .update(COMMUNITY_SUBSCRIBERS, FieldValue.arrayUnion(id))
            .await()
        localSource.addSubscriber(id)
    }

    override suspend fun unsubscribeFromCommunity(id: String) {
        auth.currentUser?.let {
            store.collection(USER_COLLECTION)
                .document(it.uid)
                .update(SUBSCRIBED_IDS, FieldValue.arrayRemove(id))
                .await()
        }
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .update(COMMUNITY_SUBSCRIBERS, FieldValue.arrayRemove(id))
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

    private fun generateId(): String = store.collection(NEWS_COMMUNITY).document().id

    companion object {
        const val COMMUNITY_OWNER = "owner"
        const val BACKGROUND = "background"
        const val OWNED_NEWS_COMMUNITIES = "Owned_news_communities"
        const val USER_COLLECTION = "Users"
        const val NEWS_COMMUNITY = "News_community"
        const val COMMUNITY_SUBSCRIBERS = "community_subscribers"
        const val COMMUNITY_ID = "community_id"
        const val COMMUNITY_TITLE = "community_title"
        const val COMMUNITY_DESCRIPTION = "community_description"
        const val COMMUNITY_AVATAR = "community_avatar"
        const val SUBSCRIBED_IDS = "subscribed_ids"
        const val OWNER = "owner"
        const val NONE = "none"
    }

}