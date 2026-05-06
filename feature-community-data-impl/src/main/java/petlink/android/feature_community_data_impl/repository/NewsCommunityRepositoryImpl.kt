package petlink.android.feature_community_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import javax.inject.Inject
import kotlin.collections.map
import kotlin.random.Random

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
        val query = store.collection(NEWS_COMMUNITY)
            .whereIn(FieldPath.documentId(), subscribedIds)
            .get()
            .await()
        return query.map {
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
    }

    override suspend fun getNewsCommunity(): List<NewsCommunityDto> {
        val snapshot = auth.currentUser?.let {
            store.collection(USER_COLLECTION)
                .document(it.uid)
                .get()
                .await()
        }
        val subscribedIds = (snapshot?.get(SUBSCRIBED_IDS) as List<*>).map { it as String }.toList()
        val owned = (snapshot.get(OWNED_NEWS_COMMUNITIES) as List<*>).map { it as String }.toList()
        val query = store.collection(NEWS_COMMUNITY)
            .whereNotEqualTo(OWNER, auth.currentUser?.uid)
            .whereNotIn(FieldPath.documentId(), subscribedIds)
            .get()
            .await()
        return query.filter { !owned.contains(it.id) && !subscribedIds.contains(it.id) }.map {
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
        }.toList()
    }

    override suspend fun addNewsCommunity(
        title: String,
        description: String,
        avatar: String,
        background: String
    ): String {
        val uid = auth.currentUser?.uid ?: NONE
        val id = generateId().toString()
        store.collection(NEWS_COMMUNITY)
            .document(id)
            .set(
                hashMapOf(
                    COMMUNITY_ID to id,
                    COMMUNITY_TITLE to title,
                    COMMUNITY_DESCRIPTION to description,
                    COMMUNITY_AVATAR to avatar,
                    COMMUNITY_OWNER to uid,
                    COMMUNITY_SUBSCRIBERS to emptyArray<String>(),
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
        return id
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

    private fun generateId() = Random.nextInt()

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