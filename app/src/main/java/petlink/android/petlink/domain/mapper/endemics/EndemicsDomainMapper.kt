package petlink.android.petlink.domain.mapper.endemics

import petlink.android.petlink.data.repository.endemic.dto.EndemicDto
import petlink.android.petlink.domain.model.endemics.EndemicDomain

fun EndemicDto.toDomain() =
    EndemicDomain(
        image = image,
        scienceName = scienceName,
        commonName = commonName,
        group = group
    )