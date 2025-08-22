package petlink.android.petlink.domain.mapper.dog

import petlink.android.petlink.data.repository.dog.dto.DogImageDto
import petlink.android.petlink.domain.model.dog.DogImageDomain

fun DogImageDto.toDomain() =
    DogImageDomain(
        image = image
    )