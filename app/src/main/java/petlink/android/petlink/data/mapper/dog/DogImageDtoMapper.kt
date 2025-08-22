package petlink.android.petlink.data.mapper.dog

import petlink.android.petlink.data.api.model.dog.DogImage
import petlink.android.petlink.data.repository.dog.dto.DogImageDto

fun DogImage.toDto() = DogImageDto(image = image)