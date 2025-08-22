package petlink.android.petlink.domain.mapper.dog

import petlink.android.petlink.data.repository.dog.dto.DogFactDto
import petlink.android.petlink.domain.model.dog.DogFactDomain

fun DogFactDto.toDomain() =
    DogFactDomain(
        fact = fact
    )