package petlink.android.petlink.domain.mapper.cat

import petlink.android.petlink.data.repository.cat.dto.CatFactDto
import petlink.android.petlink.domain.model.cat.CatFactDomain

fun CatFactDto.toDomain() =
    CatFactDomain(
        fact = fact
    )