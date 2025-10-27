package petlink.android.petlink.data.source.local.user

import petlink.android.petlink.data.local_database.provider.user.UserProvider
import petlink.android.petlink.data.mapper.user.toDto
import petlink.android.petlink.data.repository.user.dto.OwnerDto
import petlink.android.petlink.data.repository.user.dto.PetDto
import petlink.android.petlink.data.repository.user.dto.UserDto
import petlink.android.petlink.data.source.local.user.mapper.toLocalDb
import javax.inject.Inject

class UserLocalSourceImpl @Inject constructor(): UserLocalSource {
    override suspend fun getUserById(userId: String): UserDto? =
        UserProvider().getUser(userId)?.toDto()

    override suspend fun updateBackground(
        userId: String,
        background: String
    ) {
        UserProvider().updateBackground(
            userId = userId,
            background = background
        )
    }

    override suspend fun insertUser(
        userId: String,
        background: String,
        pet: PetDto,
        owner: OwnerDto
    ) {
        UserProvider().insertUser(
            userId = userId,
            background = background,
            pet = pet.toLocalDb(),
            owner = owner.toLocalDb()
        )
    }

    override suspend fun updatePetData(
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
    ) {
        UserProvider().updatePetData(
            userId = userId,
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

    override suspend fun updateOwnerData(
        userId: String,
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    ) {
        UserProvider().updateOwnerData(
            userId = userId,
            imageUri = imageUri,
            name = name,
            surname = surname,
            birthday = birthday,
            gender = gender,
            city = city
        )
    }

    override suspend fun deleteUser(userId: String) {
        UserProvider().deleteUser(userId)
    }

    override suspend fun deleteAll() {
        UserProvider().deleteAll()
    }
}