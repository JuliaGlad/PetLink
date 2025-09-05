package petlink.android.petlink.data.source.local.user

import petlink.android.petlink.data.repository.user.dto.OwnerDto
import petlink.android.petlink.data.repository.user.dto.PetDto
import petlink.android.petlink.data.repository.user.dto.UserDto

interface UserLocalSource {

    suspend fun getUserById(userId: String): UserDto?

    suspend fun updateBackground(
        userId: String,
        background: String
    )

    suspend fun insertUser(
        userId: String,
        background: String,
        pet: PetDto,
        owner: OwnerDto
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