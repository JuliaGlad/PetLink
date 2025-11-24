package petlink.android.feature_profile_data_impl.local_sorce

import petlink.android.feature_profile_data.local_source.UserLocalSource
import petlink.android.feature_profile_data.mapper.dto.toDto
import petlink.android.feature_profile_data.mapper.local_db.toLocalDb
import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data_impl.local_db.UserProvider
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabase
import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_domain.model.user_account.UserDomain
import javax.inject.Inject

class UserLocalSourceImpl @Inject constructor(
    private val profileDatabase: ProfileDatabase
): UserLocalSource {
    override suspend fun getUserById(userId: String): UserDomain? =
        UserProvider(profileDatabase).getUser(userId)?.toDto()?.toDomain()

    override suspend fun updateBackground(
        userId: String,
        background: String
    ) {
        UserProvider(profileDatabase).updateBackground(
            userId = userId,
            background = background
        )
    }

    override suspend fun insertUser(
        userId: String,
        background: String,
        pet: PetDomain,
        owner: OwnerDomain
    ) {
        UserProvider(profileDatabase).insertUser(
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
        UserProvider(profileDatabase).updatePetData(
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
        UserProvider(profileDatabase).updateOwnerData(
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
        UserProvider(profileDatabase).deleteUser(userId)
    }

    override suspend fun deleteAll() {
        UserProvider(profileDatabase).deleteAll()
    }
}