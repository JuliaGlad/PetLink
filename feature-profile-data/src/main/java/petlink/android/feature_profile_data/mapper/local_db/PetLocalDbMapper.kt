package petlink.android.feature_profile_data.mapper.local_db

import petlink.android.feature_profile_data.local_source.entity.PetLocalDb
import petlink.android.feature_profile_domain.model.pet.PetDomain

fun PetDomain.toLocalDb() =
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