package petlink.android.petlink.data.repository.user.user_account

import petlink.android.petlink.data.repository.user.dto.OwnerDto
import petlink.android.petlink.data.repository.user.dto.PetDto
import petlink.android.petlink.data.repository.user.dto.UserDto

interface UserAccountRepository {

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

    suspend fun getUserData(): UserDto?

    suspend fun addUserData(
        userId: String,
        ownerDto: OwnerDto,
        petDto: PetDto
    )
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