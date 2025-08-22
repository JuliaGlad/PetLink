package petlink.android.petlink.domain.mapper.user.owner

import petlink.android.petlink.data.repository.user.dto.OwnerDto
import petlink.android.petlink.domain.model.user.owner.OwnerDomain

fun OwnerDto.toDomain() =
    OwnerDomain(
        imageUri = imageUri,
        name = name,
        surname = surname,
        city = city,
        birthday = birthday,
        gender = gender
    )