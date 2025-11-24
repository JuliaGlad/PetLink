package petlink.android.feature_profile_data.mapper

import petlink.android.feature_profile_data.dto.OwnerDto
import petlink.android.feature_profile_data.dto.PetDto
import petlink.android.feature_profile_data.dto.UserDto
import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_domain.model.user_account.UserDomain

fun UserDto.toDomain() =
    UserDomain(
        userId = userId,
        background = background,
        pet = petDto.toDomain(),
        owner = ownerDto.toDomain()
    )

fun PetDto.toDomain() =
    PetDomain(
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

fun OwnerDto.toDomain() =
    OwnerDomain(
        imageUri = imageUri,
        name = name,
        surname = surname,
        birthday = birthday,
        gender = gender,
        city = city
    )