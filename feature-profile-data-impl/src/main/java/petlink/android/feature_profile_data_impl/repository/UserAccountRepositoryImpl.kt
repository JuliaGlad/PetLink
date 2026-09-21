package petlink.android.feature_profile_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import petlink.android.feature_profile_data.dto.OwnerDto
import petlink.android.feature_profile_data.dto.PetDto
import petlink.android.feature_profile_data.dto.UserDto
import petlink.android.feature_profile_data.dto.UserPostCommentDto
import petlink.android.feature_profile_data.dto.UserPostDto
import petlink.android.feature_profile_data.local_source.UserLocalSource
import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserDomain
import javax.inject.Inject

class UserAccountRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: UserLocalSource
) : UserAccountRepository {

    override suspend fun updateBackground(uri: String) {
        withContext(Dispatchers.IO) {
            auth.currentUser?.uid?.let { uid ->
                store.collection(USER_COLLECTION)
                    .document(uid)
                    .update(BACKGROUND, uri)
                    .await()
                localSource.updateBackground(uid, uri)
            }
        }
    }

    override suspend fun editOwnerData(
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    ) {
        val updates = mapOf(
            OWNER_IMAGE to imageUri,
            OWNER_NAME to name,
            OWNER_SURNAME to surname,
            OWNER_BIRTHDAY to birthday,
            OWNER_GENDER to gender,
            OWNER_CITY to city,
            SUBSCRIBED_IDS to arrayListOf<String>()
        )
        withContext(Dispatchers.IO) {
            auth.currentUser?.let {
                val uid = it.uid
                updateUserDataFields(uid, updates)
                localSource.updateOwnerData(
                    userId = uid,
                    imageUri = imageUri,
                    name = name,
                    surname = surname,
                    birthday = birthday,
                    gender = gender,
                    city = city
                )
            }
        }
    }

    override suspend fun editPetData(
        imageUri: String?,
        name: String?,
        birthday: String?,
        petType: String?,
        gender: String?,
        description: String?,
        games: String?,
        places: String?,
        food: String?
    ) {
        val updates = mapOf(
            PET_IMAGE to imageUri,
            PET_NAME to name,
            PET_BIRTHDAY to birthday,
            PET_TYPE to petType,
            PET_GENDER to gender,
            PET_DESCRIPTION to description,
            PET_GAMES to games,
            PET_PLACES to places,
            PET_FOOD to food
        )
        withContext(Dispatchers.IO) {
            auth.currentUser?.let {
                updateUserDataFields(it.uid, updates)
                localSource.updatePetData(
                    userId = it.uid,
                    imageUri = imageUri,
                    name = name,
                    birthday = birthday,
                    petType = petType,
                    gender = gender,
                    description = description,
                    games = games,
                    places = places,
                    food = food
                )
            }
        }
    }

    override suspend fun getUserData(): UserDomain? {
        val uid = auth.currentUser?.uid ?: return null
        return localSource.getUserById(uid) ?: getUserData(uid)
    }

    override suspend fun getUserData(userId: String): UserDomain? {
        return withContext(Dispatchers.IO) {
            val data = store.collection(USER_COLLECTION)
                .document(userId)
                .get()
                .await()
            if (!data.exists()) return@withContext null
            UserDto(
                userId = userId,
                background = data.getStringOrEmpty(BACKGROUND),
                petDto = PetDto(
                    imageUri = data.getStringOrEmpty(PET_IMAGE),
                    name = data.getStringOrEmpty(PET_NAME),
                    birthday = data.getStringOrEmpty(PET_BIRTHDAY),
                    petType = data.getStringOrEmpty(PET_TYPE),
                    gender = data.getStringOrEmpty(PET_GENDER),
                    description = data.getStringOrEmpty(PET_DESCRIPTION),
                    games = data.getStringOrEmpty(PET_GAMES),
                    places = data.getStringOrEmpty(PET_PLACES),
                    food = data.getStringOrEmpty(PET_FOOD)
                ),
                ownerDto = OwnerDto(
                    imageUri = data.getStringOrEmpty(OWNER_IMAGE),
                    name = data.getStringOrEmpty(OWNER_NAME),
                    birthday = data.getStringOrEmpty(OWNER_BIRTHDAY),
                    gender = data.getStringOrEmpty(OWNER_GENDER),
                    surname = data.getStringOrEmpty(OWNER_SURNAME),
                    city = data.getStringOrEmpty(OWNER_CITY)
                )
            ).toDomain()
        }
    }

    override suspend fun addUserData(
        petImageUri: String,
        petName: String,
        petBirthday: String,
        petType: String,
        petGender: String,
        imageUri: String,
        name: String,
        surname: String,
        birthday: String,
        gender: String,
        city: String
    ) {
        withContext(Dispatchers.IO) {
            auth.currentUser?.let {
                store.collection(USER_COLLECTION)
                    .document(it.uid)
                    .set(
                        hashMapOf(
                            BACKGROUND to "",
                            PET_NAME to petName,
                            PET_BIRTHDAY to petBirthday,
                            PET_TYPE to petType,
                            PET_IMAGE to petImageUri,
                            PET_GENDER to petGender,
                            PET_DESCRIPTION to "",
                            PET_FOOD to "",
                            PET_PLACES to "",
                            PET_GAMES to "",
                            OWNER_NAME to name,
                            OWNER_SURNAME to surname,
                            OWNER_BIRTHDAY to birthday,
                            OWNER_GENDER to gender,
                            OWNER_CITY to city,
                            OWNER_IMAGE to imageUri
                        )
                    ).await()
                addUserToLocalDb(
                    it.uid,
                    petImageUri,
                    petName,
                    petBirthday,
                    petType,
                    gender,
                    imageUri,
                    name,
                    surname,
                    birthday,
                    city
                )
            }
        }
    }

    override suspend fun createPost(
        title: String,
        description: String,
        photos: List<String>
    ): UserPostDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: error("User is not authenticated")
            val createdAt = System.currentTimeMillis()
            val ref = store.collection(USER_COLLECTION)
                .document(uid)
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
                    POST_COMMENTS_COUNT to 0,
                    POST_CREATED_AT to createdAt
                )
            ).await()
            UserPostDto(
                id = ref.id,
                title = title,
                description = description,
                photos = photos,
                createdAt = createdAt
            )
        }
    }

    override suspend fun getPosts(): List<UserPostDto> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return getPosts(uid)
    }

    override suspend fun getPosts(userId: String): List<UserPostDto> {
        return withContext(Dispatchers.IO) {
            val currentUid = auth.currentUser?.uid.orEmpty()
            store.collection(USER_COLLECTION)
                .document(userId)
                .collection(POSTS)
                .orderBy(POST_CREATED_AT, Query.Direction.DESCENDING)
                .get()
                .await()
                .documents
                .map { it.toPostDto(currentUid) }
        }
    }

    override suspend fun togglePostLike(userId: String, postId: String): UserPostDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: error("User is not authenticated")
            val ownerId = userId.ifBlank { uid }
            val ref = postRef(ownerId, postId)
            val likes = (ref.get().await().get(POST_LIKES) as? List<*>)
                ?.mapNotNull { it as? String }
                .orEmpty()
            if (likes.contains(uid)) {
                ref.update(POST_LIKES, FieldValue.arrayRemove(uid)).await()
            } else {
                ref.update(POST_LIKES, FieldValue.arrayUnion(uid)).await()
            }
            ref.get().await().toPostDto(uid)
        }
    }

    override suspend fun markPostViewed(userId: String, postId: String): UserPostDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: error("User is not authenticated")
            val ownerId = userId.ifBlank { uid }
            val ref = postRef(ownerId, postId)
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
            ref.get().await().toPostDto(uid)
        }
    }

    override suspend fun getPostComments(userId: String, postId: String): List<UserPostCommentDto> {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid.orEmpty()
            val ownerId = userId.ifBlank { uid }
            postRef(ownerId, postId)
                .collection(POST_COMMENTS)
                .get()
                .await()
                .map { snapshot -> snapshot.toCommentDto(uid) }
        }
    }

    override suspend fun addPostComment(
        userId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>
    ): UserPostCommentDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: error("User is not authenticated")
            val ownerId = userId.ifBlank { uid }
            val user = store.collection(USER_COLLECTION).document(uid).get().await()
            val senderName = listOf(
                user.getString(OWNER_NAME).orEmpty(),
                user.getString(OWNER_SURNAME).orEmpty()
            ).filter { it.isNotBlank() }.joinToString(" ")
            val senderAvatar = user.getString(OWNER_IMAGE).orEmpty()
            val ref = postRef(ownerId, postId).collection(POST_COMMENTS).document()
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
            postRef(ownerId, postId)
                .update(POST_COMMENTS_COUNT, FieldValue.increment(1))
                .await()
            UserPostCommentDto(
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
        userId: String,
        postId: String,
        commentId: String
    ): UserPostCommentDto {
        return withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: error("User is not authenticated")
            val ownerId = userId.ifBlank { uid }
            val ref = postRef(ownerId, postId).collection(POST_COMMENTS).document(commentId)
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

    private fun postRef(userId: String, postId: String) =
        store.collection(USER_COLLECTION)
            .document(userId)
            .collection(POSTS)
            .document(postId)

    private fun DocumentSnapshot.toCommentDto(uid: String): UserPostCommentDto {
        val likes = (get(POST_LIKES) as? List<*>)
            ?.mapNotNull { it as? String }
            .orEmpty()
        return UserPostCommentDto(
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

    private suspend fun addUserToLocalDb(
        userId: String,
        petImageUri: String,
        petName: String,
        petBirthday: String,
        petType: String,
        gender: String,
        imageUri: String,
        name: String,
        surname: String,
        birthday: String,
        city: String
    ) {
        localSource.insertUser(
            userId = userId,
            background = "",
            pet = PetDto(
                imageUri = petImageUri,
                name = petName,
                birthday = petBirthday,
                petType = petType,
                gender = gender,
                description = "",
                games = "",
                places = "",
                food = ""
            ).toDomain(),
            owner = OwnerDto(
                imageUri = imageUri,
                name = name,
                surname = surname,
                birthday = birthday,
                gender = gender,
                city = city
            ).toDomain()
        )
    }

    private suspend fun updateUserDataFields(userId: String, updates: Map<String, Any?>) {
        val filteredUpdates = updates.filterValues { it != null }.mapValues { it.value!! }

        withContext(Dispatchers.IO) {
            store.collection(USER_COLLECTION)
                .document(userId)
                .update(filteredUpdates)
                .await()
        }
    }

    private fun DocumentSnapshot.toPostDto(uid: String): UserPostDto {
        val likes = (get(POST_LIKES) as? List<*>)?.mapNotNull { it as? String }.orEmpty()
        return UserPostDto(
            id = id,
            title = getString(POST_TITLE).orEmpty(),
            description = getString(POST_DESCRIPTION).orEmpty(),
            photos = (get(POST_PHOTO) as? List<*>)?.mapNotNull { it as? String }.orEmpty(),
            likesCount = likes.size,
            likedByMe = likes.contains(uid),
            commentsCount = getLong(POST_COMMENTS_COUNT)?.toInt() ?: 0,
            viewsCount = getLong(POST_VIEWS)?.toInt() ?: 0,
            createdAt = getLong(POST_CREATED_AT) ?: 0L
        )
    }

    private fun DocumentSnapshot.getStringOrEmpty(field: String): String =
        get(field)?.toString() ?: ""

    companion object {
        const val BACKGROUND = "Background"
        const val USER_COLLECTION = "Users"
        const val OWNER_IMAGE: String = "OwnerImage"
        const val OWNER_NAME: String = "OwnerName"
        const val OWNER_SURNAME: String = "OwnerSurname"
        const val OWNER_BIRTHDAY: String = "OwnerBirthday"
        const val OWNER_GENDER: String = "OwnerGender"
        const val OWNER_CITY: String = "OwnerCity"
        const val PET_IMAGE: String = "PetImage"
        const val PET_NAME: String = "PetName"
        const val PET_BIRTHDAY: String = "PetBirthday"
        const val PET_GENDER: String = "PetGender"
        const val PET_TYPE: String = "PetType"
        const val PET_DESCRIPTION: String = "PetDescription"
        const val PET_FOOD: String = "PetFood"
        const val PET_GAMES = "PetGames"
        const val PET_PLACES = "PetPlaces"
        const val SUBSCRIBED_IDS = "subscribed_ids"
        const val POSTS = "Posts"
        const val POST_TITLE = "Title"
        const val POST_DESCRIPTION = "Description"
        const val POST_PHOTO = "Photos"
        const val POST_LIKES = "Likes"
        const val POST_VIEWS = "Views"
        const val POST_VIEWED_BY = "ViewedBy"
        const val POST_COMMENTS_COUNT = "CommentsCount"
        const val POST_COMMENTS = "Comments"
        const val POST_CREATED_AT = "CreatedAt"
        const val COMMENT_PARENT_ID = "parent_id"
        const val COMMENT_PHOTOS = "photos"
        const val MESSAGE_SENDER_ID = "sender_id"
        const val MESSAGE_SENDER_NAME = "sender_name"
        const val MESSAGE_SENDER_AVATAR = "sender_avatar"
        const val MESSAGE_TEXT = "text"
    }
}