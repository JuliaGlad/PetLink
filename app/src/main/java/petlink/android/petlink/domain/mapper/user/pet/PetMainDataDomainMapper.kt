package petlink.android.petlink.domain.mapper.user.pet

import petlink.android.petlink.data.repository.user.dto.PetDto
import petlink.android.petlink.domain.model.user.pet.PetMainDataDomain

fun PetDto.toDomainMainData() =
    PetMainDataDomain(
        imageUri = imageUri,
        name = name
    )