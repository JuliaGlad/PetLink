package petlink.android.petlink.domain.mapper.cat

import petlink.android.petlink.data.repository.cat.dto.CatImageDto
import petlink.android.petlink.domain.model.cat.CatImageDomain

fun CatImageDto.toDomain() =
    CatImageDomain(
        image = image
    )