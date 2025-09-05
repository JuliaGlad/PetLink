package petlink.android.petlink.data.repository.user.user_account

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import petlink.android.petlink.data.repository.user.dto.OwnerDto
import petlink.android.petlink.data.repository.user.dto.PetDto
import petlink.android.petlink.data.repository.user.dto.UserDto
import petlink.android.petlink.data.source.local.user.UserLocalSource

class UserAccountRepositoryImpl @javax.inject.Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: UserLocalSource
) : UserAccountRepository {

    override suspend fun updateBackground(uri: String) {
        auth.currentUser?.uid?.let { uid ->
            store.collection(USER_COLLECTION)
                .document(uid)
                .update(BACKGROUND, uri)
                .await()
            localSource.updateBackground(uid, uri)
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
            OWNER_CITY to city
        )
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

    override suspend fun getUserData(): UserDto? {
        val result = auth.currentUser?.let {
            val local = localSource.getUserById(it.uid)
            val result: UserDto = local
                ?: withContext(Dispatchers.IO) {
                    val data = store.collection(USER_COLLECTION)
                        .document(it.uid)
                        .get()
                        .await()
                    val petDto = PetDto(
                        imageUri = data.getStringOrEmpty(PET_IMAGE),
                        name = data.getStringOrEmpty(PET_NAME),
                        birthday = data.getStringOrEmpty(PET_BIRTHDAY),
                        petType = data.getStringOrEmpty(PET_TYPE),
                        gender = data.getStringOrEmpty(PET_GENDER),
                        description = data.getStringOrEmpty(PET_DESCRIPTION),
                        games = data.getStringOrEmpty(PET_GAMES),
                        places = data.getStringOrEmpty(PET_PLACES),
                        food = data.getStringOrEmpty(PET_FOOD)
                    )
                    val ownerDto = OwnerDto(
                        imageUri = data.getStringOrEmpty(OWNER_IMAGE),
                        name = data.getStringOrEmpty(OWNER_NAME),
                        birthday = data.getStringOrEmpty(OWNER_BIRTHDAY),
                        gender = data.getStringOrEmpty(OWNER_GENDER),
                        surname = data.getStringOrEmpty(OWNER_SURNAME),
                        city = data.getStringOrEmpty(OWNER_CITY)
                    )
                    UserDto(
                        userId = it.uid,
                        background = data.getStringOrEmpty(BACKGROUND),
                        petDto = petDto,
                        ownerDto = ownerDto
                    )
                }
            result
        }
        return result
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
            ),
            owner = OwnerDto(
                imageUri = imageUri,
                name = name,
                surname = surname,
                birthday = birthday,
                gender = gender,
                city = city
            )
        )
    }

    private suspend fun updateUserDataFields(userId: String, updates: Map<String, Any?>) {
        val filteredUpdates = updates.filterValues { it != null }.mapValues { it.value!! }

        store.collection(USER_COLLECTION)
            .document(userId)
            .update(filteredUpdates)
            .await()
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
    }
}