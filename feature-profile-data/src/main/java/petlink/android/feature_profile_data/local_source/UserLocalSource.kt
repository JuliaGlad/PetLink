package petlink.android.feature_profile_data.local_source

import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_domain.model.user_account.UserDomain

interface UserLocalSource {

    suspend fun getUserById(userId: String): UserDomain?

    suspend fun updateBackground(
        userId: String,
        background: String
    )

    suspend fun insertUser(
        userId: String,
        background: String,
        pet: PetDomain,
        owner: OwnerDomain
    )

    suspend fun updatePetData(
        userId: String,
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

    suspend fun updateOwnerData(
        userId: String,
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    )

    suspend fun deleteUser(userId: String)

    suspend fun deleteAll()

}