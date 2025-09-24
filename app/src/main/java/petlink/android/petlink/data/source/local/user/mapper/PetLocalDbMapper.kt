package petlink.android.petlink.data.source.local.user.mapper

import petlink.android.petlink.data.local_database.entity.user.PetLocalDb
import petlink.android.petlink.data.repository.user.dto.PetDto

fun PetDto.toLocalDb() =
    PetLocalDb(
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