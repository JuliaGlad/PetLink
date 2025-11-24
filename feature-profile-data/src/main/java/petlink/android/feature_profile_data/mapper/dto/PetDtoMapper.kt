package petlink.android.feature_profile_data.mapper.dto

import petlink.android.feature_profile_data.dto.PetDto
import petlink.android.feature_profile_data.local_source.entity.PetLocalDb

fun PetLocalDb.toDto() =
    PetDto(
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