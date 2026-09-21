package petlink.android.feature_community_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_data.dto.ChatMessageDto
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) : ChatRepository {

    override suspend fun getChats(): List<NewsCommunityDto> {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@withContext emptyList()
            store.collection(CHATS)
                .whereArrayContains(CHAT_PARTICIPANTS, uid)
                .get()
                .await()
                .map { snapshot ->
                    NewsCommunityDto(
                        id = snapshot.id,
                        ownerId = snapshot.getString(CHAT_OWNER).orEmpty(),
                        subscribers = (snapshot.get(CHAT_PARTICIPANTS) as? List<*>)
                            ?.mapNotNull { it as? String }
                            .orEmpty(),
                        title = snapshot.getString(CHAT_TITLE).orEmpty(),
                        description = snapshot.getString(CHAT_LAST_MESSAGE).orEmpty(),
                        avatar = snapshot.getString(CHAT_AVATAR).orEmpty(),
                        role = RoleInCommunityTag.Subscribed,
                        background = "",
                        communityType = CHAT_TYPE
                    )
                }
        }
    }

    override suspend fun createChat(title: String, avatar: String, participantId: String): String {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val id = store.collection(CHATS).document().id
            store.collection(CHATS)
                .document(id)
                .set(
                    hashMapOf(
                        CHAT_TITLE to title,
                        CHAT_AVATAR to avatar,
                        CHAT_OWNER to uid,
                        CHAT_PARTICIPANTS to listOf(uid, participantId).distinct(),
                        CHAT_LAST_MESSAGE to ""
                    )
                ).await()
            id
        }
    }

    override suspend fun getChatMessages(chatId: String): List<ChatMessageDto> {
        return withContext(Dispatchers.IO) {
            store.collection(CHATS)
                .document(chatId)
                .collection(MESSAGES)
                .get()
                .await()
                .map { snapshot ->
                    ChatMessageDto(
                        id = snapshot.id,
                        senderId = snapshot.getString(MESSAGE_SENDER_ID).orEmpty(),
                        senderName = snapshot.getString(MESSAGE_SENDER_NAME).orEmpty(),
                        senderAvatar = snapshot.getString(MESSAGE_SENDER_AVATAR).orEmpty(),
                        text = snapshot.getString(MESSAGE_TEXT).orEmpty()
                    )
                }
        }
    }

    override suspend fun sendChatMessage(chatId: String, text: String): ChatMessageDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: NONE
            val user = store.collection(USER_COLLECTION).document(uid).get().await()
            val senderName = listOf(
                user.getString(OWNER_NAME).orEmpty(),
                user.getString(OWNER_SURNAME).orEmpty()
            ).filter { it.isNotBlank() }.joinToString(" ")
            val senderAvatar = user.getString(OWNER_IMAGE).orEmpty()
            val ref = store.collection(CHATS)
                .document(chatId)
                .collection(MESSAGES)
                .document()
            ref.set(
                hashMapOf(
                    MESSAGE_SENDER_ID to uid,
                    MESSAGE_SENDER_NAME to senderName,
                    MESSAGE_SENDER_AVATAR to senderAvatar,
                    MESSAGE_TEXT to text
                )
            ).await()
            store.collection(CHATS)
                .document(chatId)
                .update(CHAT_LAST_MESSAGE, text)
                .await()
            ChatMessageDto(
                id = ref.id,
                senderId = uid,
                senderName = senderName,
                senderAvatar = senderAvatar,
                text = text
            )
        }
    }

    override suspend fun getFriends(): List<NewsCommunityDto> {
        return withContext(Dispatchers.IO) {
            val friendIds = currentUserStringList(FRIENDS_IDS)
            if (friendIds.isEmpty()) emptyList()
            else getUsersByIds(friendIds, RoleInCommunityTag.Subscribed)
        }
    }

    override suspend fun getOtherUsers(): List<NewsCommunityDto> {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@withContext emptyList()
            val friendIds = currentUserStringList(FRIENDS_IDS).toSet()
            store.collection(USER_COLLECTION)
                .get()
                .await()
                .documents
                .filter { it.id != uid && !friendIds.contains(it.id) }
                .map { it.toUserDto(RoleInCommunityTag.Unsubscribed) }
        }
    }

    override suspend fun getUsersByIds(ids: List<String>): List<NewsCommunityDto> =
        getUsersByIds(ids, RoleInCommunityTag.Unsubscribed)

    override suspend fun getUserById(id: String): NewsCommunityDto {
        return withContext(Dispatchers.IO) {
            val snapshot = store.collection(USER_COLLECTION)
                .document(id)
                .get()
                .await()
            val friendIds = currentUserStringList(FRIENDS_IDS)
            val role = if (friendIds.contains(id)) RoleInCommunityTag.Subscribed
            else RoleInCommunityTag.Unsubscribed
            snapshot.toUserDto(role)
        }
    }

    override suspend fun addFriend(userId: String) {
        withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@withContext
            val batch = store.batch()
            batch.set(
                store.collection(USER_COLLECTION).document(uid),
                mapOf(FRIENDS_IDS to FieldValue.arrayUnion(userId)),
                SetOptions.merge()
            )
            batch.set(
                store.collection(USER_COLLECTION).document(userId),
                mapOf(FRIENDS_IDS to FieldValue.arrayUnion(uid)),
                SetOptions.merge()
            )
            batch.commit().await()
        }
    }

    override suspend fun removeFriend(userId: String) {
        withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@withContext
            val batch = store.batch()
            batch.set(
                store.collection(USER_COLLECTION).document(uid),
                mapOf(FRIENDS_IDS to FieldValue.arrayRemove(userId)),
                SetOptions.merge()
            )
            batch.set(
                store.collection(USER_COLLECTION).document(userId),
                mapOf(FRIENDS_IDS to FieldValue.arrayRemove(uid)),
                SetOptions.merge()
            )
            batch.commit().await()
        }
    }

    private suspend fun getUsersByIds(
        ids: List<String>,
        role: RoleInCommunityTag
    ): List<NewsCommunityDto> {
        if (ids.isEmpty()) return emptyList()
        return ids.chunked(10).flatMap { chunk ->
            store.collection(USER_COLLECTION)
                .whereIn(FieldPath.documentId(), chunk)
                .get()
                .await()
                .documents
        }.map { it.toUserDto(role) }
    }

    private suspend fun currentUserStringList(field: String): List<String> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        val snapshot = store.collection(USER_COLLECTION)
            .document(uid)
            .get()
            .await()
        return (snapshot.get(field) as? List<*>)?.mapNotNull { it as? String }.orEmpty()
    }

    private fun DocumentSnapshot.toUserDto(role: RoleInCommunityTag): NewsCommunityDto {
        val ownerName = listOf(
            getString(OWNER_NAME).orEmpty(),
            getString(OWNER_SURNAME).orEmpty()
        ).filter { it.isNotBlank() }.joinToString(" ")
        val petName = getString(PET_NAME).orEmpty()
        val friendIds = (get(FRIENDS_IDS) as? List<*>)?.mapNotNull { it as? String }.orEmpty()
        return NewsCommunityDto(
            id = id,
            ownerId = id,
            subscribers = friendIds,
            title = petName.ifBlank { ownerName },
            description = ownerName,
            avatar = getString(PET_IMAGE).orEmpty().ifBlank { getString(OWNER_IMAGE).orEmpty() },
            role = role,
            background = getString(OWNER_IMAGE).orEmpty(),
            communityType = USER_TYPE
        )
    }

    companion object {
        const val USER_COLLECTION = "Users"
        const val FRIENDS_IDS = "friends_ids"
        const val NONE = "none"
        const val CHATS = "Chats"
        const val CHAT_TITLE = "chat_title"
        const val CHAT_AVATAR = "chat_avatar"
        const val CHAT_OWNER = "chat_owner"
        const val CHAT_PARTICIPANTS = "chat_participants"
        const val CHAT_LAST_MESSAGE = "chat_last_message"
        const val CHAT_TYPE = "chat"
        const val USER_TYPE = "user"
        const val MESSAGES = "Messages"
        const val MESSAGE_SENDER_ID = "sender_id"
        const val MESSAGE_SENDER_NAME = "sender_name"
        const val MESSAGE_SENDER_AVATAR = "sender_avatar"
        const val MESSAGE_TEXT = "text"
        const val OWNER_IMAGE = "OwnerImage"
        const val OWNER_NAME = "OwnerName"
        const val OWNER_SURNAME = "OwnerSurname"
        const val PET_IMAGE = "PetImage"
        const val PET_NAME = "PetName"
    }
}
