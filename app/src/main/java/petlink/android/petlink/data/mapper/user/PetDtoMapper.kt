package petlink.android.petlink.data.mapper.user

import petlink.android.petlink.data.local_database.entity.PetLocalDb
import petlink.android.petlink.data.repository.user.dto.PetDto

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