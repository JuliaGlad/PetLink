package petlink.android.feature_profile_data.repository

import petlink.android.feature_profile_domain.model.user_account.UserDomain

interface UserAccountRepository {

    suspend fun updateBackground(uri: String)

    suspend fun editOwnerData(
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    )

    suspend fun editPetData(
        imageUri: String?,
        name: String?,
        birthday: String?,
        petType: String?,
        gender: String?,
        description: String?,
        games: String?,
        places: String?,
        food: String?
    )

    suspend fun getUserData(): UserDomain?


    suspend fun addUserData(
        petImageUri: String = "",
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
    )
}