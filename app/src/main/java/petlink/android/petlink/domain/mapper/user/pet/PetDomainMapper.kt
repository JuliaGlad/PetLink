package petlink.android.petlink.domain.mapper.user.pet

import petlink.android.petlink.data.repository.user.dto.PetDto
import petlink.android.petlink.domain.model.user.pet.PetDomain

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