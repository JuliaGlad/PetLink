package petlink.android.petlink.domain.mapper.user.owner

import petlink.android.petlink.data.repository.user.dto.OwnerDto
import petlink.android.petlink.domain.model.user.owner.OwnerMainDataDomain

fun OwnerDto.toDomainMainData() =
    OwnerMainDataDomain(
        imageUri = imageUri,
        name = name,
        surname = surname
    )