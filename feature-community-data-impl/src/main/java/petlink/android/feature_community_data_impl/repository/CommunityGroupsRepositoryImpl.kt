package petlink.android.feature_community_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import petlink.android.feature_community_core.CommunityStorageType
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.dto.NewsPostDto
import petlink.android.feature_community_data.dto.PostCommentDto
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.CommunityGroupsRepository

abstract class CommunityGroupsRepositoryImpl(
    private val type: String,
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: NewsCommunityLocalSource
) : CommunityGroupsRepository {

    private val communitiesCollection: String = CommunityStorageType.collectionFor(type)

    override suspend fun getOwnedCommunities(): List<NewsCommunityDto> {
        return withContext(Dispatchers.IO) {
            val query = store.collection(communitiesCollection)
                .whereEqualTo(OWNER, auth.currentUser?.uid)
                .get()
                .await()
            query.map { it.toCommunityDto(RoleInCommunityTag.Owner) }
        }
    }

    override suspend fun getSubscribedCommunities(): List<NewsCommunityDto> {
        return withContext(Dispatchers.IO) {
            val subscribedIds = currentUserStringList(SUBSCRIBED_IDS)
            if (subscribedIds.isNotEmpty()) {
                documentsByIds(communitiesCollection, subscribedIds)
                    .map { it.toCommunityDto(RoleInCommunityTag.Subscribed) }
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getOtherCommunities(): List<NewsCommunityDto> {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid
            val subscribedIds = currentUserStringList(SUBSCRIBED_IDS)
            val query = store.collection(communitiesCollection)
                .whereNotEqualTo(OWNER, uid)
                .get()
                .await()
            query.filter { !subscribedIds.contains(it.id) }
                .map { it.toCommunityDto(RoleInCommunityTag.Unsubscribed) }
        }
    }

    override suspend fun getCommunityById(id: String): NewsCommunityDto {
        return withContext(Dispatchers.IO) {
            val snapshot = store.collection(communitiesCollection)
                .document(id)
                .get()
                .await()
            snapshot.toCommunityDto(roleForCommunity(snapshot))
        }
    }

    override suspend fun getCommunityPosts(communityId: String): List<NewsPostDto> =
        withContext(Dispatchers.IO) {
            val query = store.collection(communitiesCollection)
                .document(communityId)
                .collection(POSTS)
                .get()
                .await()
            query.documents.map { snapshot -> snapshot.toPostDto(communityId) }.toList()
        }

    override suspend fun createPost(
        communityId: String,
        title: String,
        description: String,
        photos: List<String>
    ): NewsPostDto {
        return withContext(Dispatchers.IO) {
            val ref = store.collection(communitiesCollection)
                .document(communityId)
                .collection(POSTS)
                .document()
            ref.set(
                hashMapOf(
                    POST_TITLE to title,
                    POST_DESCRIPTION to description,
                    POST_PHOTO to photos,
                    POST_LIKES to emptyList<String>(),
                    POST_VIEWS to 0,
                    POST_VIEWED_BY to emptyList<String>(),
                    POST_COMMENTS_COUNT to 0
                )
            ).await()
            NewsPostDto(
                id = ref.id,
                communityId = communityId,
                title = title,
                description = description,
                photos = photos
            )
        }
    }

    override suspend fun getPost(
        communityId: String,
        postId: String
    ): NewsPostDto {
        return withContext(Dispatchers.IO) {
            val data = store.collection(communitiesCollection)
                .document(communityId)
                .collection(POSTS)
                .document(postId)
                .get()
                .await()
            data.toPostDto(communityId)
        }
    }

    override suspend fun deletePost(communityId: String, postId: String) {
        withContext(Dispatchers.IO) {
            store.collection(communitiesCollection)
                .document(communityId)
                .collection(POSTS)
                .document(postId)
                .delete()
                .await()
        }
    }

    override suspend fun editPost(
        communityId: String,
        postId: String,
        title: String,
        description: String,
        photos: List<String>
    ) {
        withContext(Dispatchers.IO) {
            val updates = mapOf(
                POST_TITLE to title,
                POST_DESCRIPTION to description,
                POST_PHOTO to photos
            )
            val ref = store.collection(communitiesCollection)
                .document(communityId)
                .collection(POSTS)
                .document(postId)
            ref.update(updates).await()
        }
    }

    override suspend fun togglePostLike(communityId: String, postId: String): NewsPostDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val ref = postRef(communityId, postId)
            val likes = (ref.get().await().get(POST_LIKES) as? List<*>)
                ?.mapNotNull { it as? String }
                .orEmpty()
            if (likes.contains(uid)) {
                ref.update(POST_LIKES, FieldValue.arrayRemove(uid)).await()
            } else {
                ref.update(POST_LIKES, FieldValue.arrayUnion(uid)).await()
            }
            ref.get().await().toPostDto(communityId)
        }
    }

    override suspend fun markPostViewed(communityId: String, postId: String): NewsPostDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val ref = postRef(communityId, postId)
            val viewedBy = (ref.get().await().get(POST_VIEWED_BY) as? List<*>)
                ?.mapNotNull { it as? String }
                .orEmpty()
            if (!viewedBy.contains(uid)) {
                ref.update(
                    mapOf(
                        POST_VIEWS to FieldValue.increment(1),
                        POST_VIEWED_BY to FieldValue.arrayUnion(uid)
                    )
                ).await()
            }
            ref.get().await().toPostDto(communityId)
        }
    }

    override suspend fun getPostComments(communityId: String, postId: String): List<PostCommentDto> {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            postRef(communityId, postId)
                .collection(POST_COMMENTS)
                .get()
                .await()
                .map { snapshot -> snapshot.toCommentDto(uid) }
        }
    }

    override suspend fun addPostComment(
        communityId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>
    ): PostCommentDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val user = store.collection(USER_COLLECTION).document(uid).get().await()
            val senderName = listOf(
                user.getString(OWNER_NAME).orEmpty(),
                user.getString(OWNER_SURNAME).orEmpty()
            ).filter { it.isNotBlank() }.joinToString(" ")
            val senderAvatar = user.getString(OWNER_IMAGE).orEmpty()
            val ref = postRef(communityId, postId).collection(POST_COMMENTS).document()
            ref.set(
                hashMapOf(
                    MESSAGE_SENDER_ID to uid,
                    MESSAGE_SENDER_NAME to senderName,
                    MESSAGE_SENDER_AVATAR to senderAvatar,
                    MESSAGE_TEXT to text,
                    COMMENT_PARENT_ID to parentId,
                    COMMENT_PHOTOS to photos,
                    POST_LIKES to emptyList<String>()
                )
            ).await()
            postRef(communityId, postId)
                .update(POST_COMMENTS_COUNT, FieldValue.increment(1))
                .await()
            PostCommentDto(
                id = ref.id,
                senderId = uid,
                senderName = senderName,
                senderAvatar = senderAvatar,
                text = text,
                parentId = parentId,
                likesCount = 0,
                likedByMe = false,
                photos = photos
            )
        }
    }

    override suspend fun toggleCommentLike(
        communityId: String,
        postId: String,
        commentId: String
    ): PostCommentDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val ref = postRef(communityId, postId).collection(POST_COMMENTS).document(commentId)
            val snapshot = ref.get().await()
            val likes = (snapshot.get(POST_LIKES) as? List<*>)
                ?.mapNotNull { it as? String }
                .orEmpty()
            if (likes.contains(uid)) {
                ref.update(POST_LIKES, FieldValue.arrayRemove(uid)).await()
            } else {
                ref.update(POST_LIKES, FieldValue.arrayUnion(uid)).await()
            }
            ref.get().await().toCommentDto(uid)
        }
    }

    override suspend fun addCommunity(
        title: String,
        description: String,
        avatar: String,
        background: String
    ): String {
        val communityID = withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val id = generateId()
            store.collection(communitiesCollection)
                .document(id)
                .set(
                    hashMapOf(
                        COMMUNITY_ID to id,
                        COMMUNITY_TITLE to title,
                        COMMUNITY_DESCRIPTION to description,
                        COMMUNITY_AVATAR to avatar,
                        COMMUNITY_OWNER to uid,
                        COMMUNITY_SUBSCRIBERS to emptyList<String>(),
                        BACKGROUND to background,
                        COMMUNITY_TYPE to type
                    )
                ).await()
            localSource.insertNewsCommunity(
                communityId = id,
                title = title,
                description = description,
                avatar = avatar,
                subscribers = mutableListOf(),
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

    override suspend fun updateCommunityData(
        id: String,
        newTitle: String?,
        newDescription: String?
    ) {
        withContext(Dispatchers.IO) {
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
    }

    override suspend fun updateCommunityAvatar(id: String, newUri: String) {
        withContext(Dispatchers.IO) {
            val updates = mapOf(
                COMMUNITY_AVATAR to newUri
            )
            updateCommunityDataFields(id, updates)
            localSource.updateNewsCommunityAvatar(
                communityId = id,
                newAvatar = newUri
            )
        }
    }

    override suspend fun updateCommunityBackground(id: String, newUri: String) {
        withContext(Dispatchers.IO) {
            val updates = mapOf(
                BACKGROUND to newUri
            )
            updateCommunityDataFields(id, updates)
            localSource.updateNewsCommunityBackground(
                communityId = id,
                newBackground = newUri
            )
        }
    }

    override suspend fun deleteCommunity(id: String) {
        withContext(Dispatchers.IO) {
            store.collection(communitiesCollection)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun subscribeToCommunity(id: String) {
        withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@withContext
            store.collection(USER_COLLECTION)
                .document(uid)
                .update(SUBSCRIBED_IDS, FieldValue.arrayUnion(id))
                .await()
            store.collection(communitiesCollection)
                .document(id)
                .update(COMMUNITY_SUBSCRIBERS, FieldValue.arrayUnion(uid))
                .await()
            localSource.addSubscriber(id, uid)
        }
    }

    override suspend fun unsubscribeFromCommunity(id: String) {
        withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@withContext
            store.collection(USER_COLLECTION)
                .document(uid)
                .update(SUBSCRIBED_IDS, FieldValue.arrayRemove(id))
                .await()
            store.collection(communitiesCollection)
                .document(id)
                .update(COMMUNITY_SUBSCRIBERS, FieldValue.arrayRemove(uid))
                .await()
            localSource.removeSubscriber(id, uid)
        }
    }

    private suspend fun updateCommunityDataFields(id: String, updates: Map<String, Any?>) {
        withContext(Dispatchers.IO) {
            val filteredUpdates = updates.filterValues { it != null }.mapValues { it.value!! }
            store.collection(communitiesCollection)
                .document(id)
                .update(filteredUpdates)
                .await()
        }
    }

    private suspend fun documentsByIds(
        collection: String,
        ids: List<String>
    ): List<DocumentSnapshot> =
        ids.chunked(10).flatMap { chunk ->
            store.collection(collection)
                .whereIn(FieldPath.documentId(), chunk)
                .get()
                .await()
                .documents
        }

    private suspend fun currentUserStringList(field: String): List<String> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        val snapshot = store.collection(USER_COLLECTION)
            .document(uid)
            .get()
            .await()
        return (snapshot.get(field) as? List<*>)?.mapNotNull { it as? String }.orEmpty()
    }

    private fun roleForCommunity(snapshot: DocumentSnapshot): RoleInCommunityTag {
        val uid = auth.currentUser?.uid
        val ownerId = snapshot.getString(COMMUNITY_OWNER)
        val subscribers = (snapshot.get(COMMUNITY_SUBSCRIBERS) as? List<*>)
            ?.mapNotNull { it as? String }
            .orEmpty()
        return when {
            ownerId == uid -> RoleInCommunityTag.Owner
            subscribers.contains(uid) -> RoleInCommunityTag.Subscribed
            else -> RoleInCommunityTag.Unsubscribed
        }
    }

    private fun postRef(communityId: String, postId: String) =
        store.collection(communitiesCollection)
            .document(communityId)
            .collection(POSTS)
            .document(postId)

    private fun DocumentSnapshot.toCommentDto(uid: String): PostCommentDto {
        val likes = (get(POST_LIKES) as? List<*>)
            ?.mapNotNull { it as? String }
            .orEmpty()
        return PostCommentDto(
            id = id,
            senderId = getString(MESSAGE_SENDER_ID).orEmpty(),
            senderName = getString(MESSAGE_SENDER_NAME).orEmpty(),
            senderAvatar = getString(MESSAGE_SENDER_AVATAR).orEmpty(),
            text = getString(MESSAGE_TEXT).orEmpty(),
            parentId = getString(COMMENT_PARENT_ID).orEmpty(),
            likesCount = likes.size,
            likedByMe = likes.contains(uid),
            photos = (get(COMMENT_PHOTOS) as? List<*>)?.mapNotNull { it as? String }.orEmpty()
        )
    }

    private fun DocumentSnapshot.toPostDto(communityId: String): NewsPostDto {
        val likes = (get(POST_LIKES) as? List<*>)?.mapNotNull { it as? String }.orEmpty()
        return NewsPostDto(
            id = id,
            communityId = communityId,
            title = getString(POST_TITLE).orEmpty(),
            description = getString(POST_DESCRIPTION).orEmpty(),
            photos = (get(POST_PHOTO) as? List<*>)?.mapNotNull { it as? String }.orEmpty(),
            likes = likes,
            views = getLong(POST_VIEWS)?.toInt() ?: 0,
            commentsCount = getLong(POST_COMMENTS_COUNT)?.toInt() ?: 0,
            likedByMe = likes.contains(auth.currentUser?.uid)
        )
    }

    private fun DocumentSnapshot.toCommunityDto(role: RoleInCommunityTag) =
        NewsCommunityDto(
            id = id,
            title = getString(COMMUNITY_TITLE).orEmpty(),
            description = getString(COMMUNITY_DESCRIPTION).orEmpty(),
            avatar = getString(COMMUNITY_AVATAR).orEmpty(),
            subscribers = (get(COMMUNITY_SUBSCRIBERS) as? List<*>)
                ?.mapNotNull { it as? String }
                .orEmpty(),
            ownerId = getString(COMMUNITY_OWNER).orEmpty(),
            role = role,
            background = getString(BACKGROUND).orEmpty(),
            communityType = getString(COMMUNITY_TYPE) ?: type
        )

    private fun generateId(): String = store.collection(communitiesCollection).document().id

    companion object {
        const val POST_TITLE = "Title"
        const val POST_DESCRIPTION = "Description"
        const val POST_PHOTO = "Photos"
        const val POST_LIKES = "Likes"
        const val POST_VIEWS = "Views"
        const val POST_VIEWED_BY = "ViewedBy"
        const val POST_COMMENTS_COUNT = "CommentsCount"
        const val POST_COMMENTS = "Comments"
        const val COMMENT_PARENT_ID = "parent_id"
        const val COMMENT_PHOTOS = "photos"
        const val POSTS = "Posts"
        const val COMMUNITY_OWNER = "owner"
        const val BACKGROUND = "background"
        const val OWNED_NEWS_COMMUNITIES = "Owned_news_communities"
        const val USER_COLLECTION = "Users"
        const val COMMUNITY_SUBSCRIBERS = "community_subscribers"
        const val COMMUNITY_ID = "community_id"
        const val COMMUNITY_TITLE = "community_title"
        const val COMMUNITY_DESCRIPTION = "community_description"
        const val COMMUNITY_AVATAR = "community_avatar"
        const val COMMUNITY_TYPE = "community_type"
        const val SUBSCRIBED_IDS = "subscribed_ids"
        const val OWNER = "owner"
        const val NONE = "none"
        const val MESSAGE_SENDER_ID = "sender_id"
        const val MESSAGE_SENDER_NAME = "sender_name"
        const val MESSAGE_SENDER_AVATAR = "sender_avatar"
        const val MESSAGE_TEXT = "text"
        const val OWNER_IMAGE = "OwnerImage"
        const val OWNER_NAME = "OwnerName"
        const val OWNER_SURNAME = "OwnerSurname"
    }
}
